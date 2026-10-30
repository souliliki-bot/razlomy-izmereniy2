#!/usr/bin/env python3
"""Validate datapack JSON and internal worldgen references for Minecraft 1.21.1."""
import json
import sys
from pathlib import Path

ROOT = Path("src/main/resources/data/razlomy_izmereniy")
errors = []
json_files = list(Path("src/main/resources").rglob("*.json"))

for path in json_files:
    try:
        json.loads(path.read_text(encoding="utf-8"))
    except Exception as exc:
        errors.append(f"{path}: invalid JSON: {exc}")

def load(path):
    try:
        return json.loads(path.read_text(encoding="utf-8"))
    except Exception:
        return None

# 1.21.1 BiomeGenerationSettings contains exactly eleven ordered feature steps.
for path in sorted((ROOT / "worldgen/biome").glob("*.json")):
    data = load(path)
    if data is None:
        continue
    features = data.get("features")
    if not isinstance(features, list) or len(features) != 11:
        errors.append(f"{path}: expected exactly 11 feature stages, got {len(features) if isinstance(features, list) else 'non-list'}")
        continue
    spawners = data.get("spawners", {})
    if not isinstance(spawners, dict):
        errors.append(f"{path}: spawners must be an object")
    else:
        for category, entries in spawners.items():
            if not isinstance(entries, list):
                errors.append(f"{path}: spawners.{category} must be a list")
                continue
            for index, entry in enumerate(entries):
                required = {"type", "weight", "minCount", "maxCount"}
                if not isinstance(entry, dict) or not required.issubset(entry):
                    errors.append(f"{path}: spawners.{category}[{index}] must contain type, weight, minCount, maxCount")

# Every dimension must resolve its own dimension type, noise settings and biome IDs.
for path in sorted((ROOT / "dimension").glob("*.json")):
    data = load(path)
    if data is None:
        continue
    stem = path.stem
    expected_type = f"razlomy_izmereniy:{stem}"
    if data.get("type") != expected_type:
        errors.append(f"{path}: expected dimension type {expected_type}, got {data.get('type')}")
    generator = data.get("generator", {})
    settings = generator.get("settings", "")
    if settings.startswith("razlomy_izmereniy:"):
        settings_path = ROOT / "worldgen/noise_settings" / (settings.split(":", 1)[1] + ".json")
        if not settings_path.is_file():
            errors.append(f"{path}: missing noise settings {settings_path}")
    biome_source = generator.get("biome_source", {})
    refs = []
    if biome_source.get("type") == "minecraft:multi_noise":
        refs = [entry.get("biome", "") for entry in biome_source.get("biomes", [])]
    if len(refs) < 3:
        errors.append(f"{path}: expected at least three biome entries, got {len(refs)}")
    for ref in refs:
        if ref.startswith("razlomy_izmereniy:"):
            biome_path = ROOT / "worldgen/biome" / (ref.split(":", 1)[1] + ".json")
            if not biome_path.is_file():
                errors.append(f"{path}: missing biome {ref}")

# Custom feature references in biome generation lists must resolve to placed features.
placed = ROOT / "worldgen/placed_feature"
for path in sorted((ROOT / "worldgen/biome").glob("*.json")):
    data = load(path)
    if data is None:
        continue
    for stage_index, stage in enumerate(data.get("features", [])):
        if not isinstance(stage, list):
            continue
        for ref in stage:
            if isinstance(ref, str) and ref.startswith("razlomy_izmereniy:"):
                feature_path = placed / (ref.split(":", 1)[1] + ".json")
                if not feature_path.is_file():
                    errors.append(f"{path}: feature stage {stage_index} references missing placed feature {ref}")

if errors:
    print("\n".join(f"ERROR: {error}" for error in errors))
    print(f"Datapack validation failed with {len(errors)} issue(s).")
    sys.exit(1)

print(f"Validated {len(json_files)} JSON files, {len(list((ROOT / 'worldgen/biome').glob('*.json')))} biomes and {len(list((ROOT / 'dimension').glob('*.json')))} dimensions.")
