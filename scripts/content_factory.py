#!/usr/bin/env python3
"""Small content generator and consistency checker for Razlomy Izmereniy (MC 1.21.1).

Examples:
  python3 scripts/content_factory.py --check
  python3 scripts/content_factory.py --item frost_charm --name "Морозный талисман" --ingredient razlomy_izmereniy:frost_ingot --texture diamond
"""
import argparse
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
RES = ROOT / "src/main/resources"
NS = "razlomy_izmereniy"
LANG = RES / "assets" / NS / "lang" / "ru_ru.json"


def check():
    errors = []
    files = sorted(RES.rglob("*.json"))
    for path in files:
        try:
            data = json.loads(path.read_text(encoding="utf-8"))
            if not isinstance(data, (dict, list)):
                errors.append(f"{path.relative_to(ROOT)}: JSON root must be object or array")
        except Exception as exc:
            errors.append(f"{path.relative_to(ROOT)}: {exc}")
    if LANG.exists():
        try:
            lang = json.loads(LANG.read_text(encoding="utf-8"))
            for key, value in lang.items():
                if not isinstance(key, str) or not isinstance(value, str):
                    errors.append(f"{LANG.relative_to(ROOT)}: translations must be string pairs")
        except Exception:
            pass
    biome_dir = RES / "data" / NS / "worldgen" / "biome"
    biomes = list(biome_dir.glob("*.json"))
    if len(biomes) < 36:
        errors.append(f"Expected at least 36 dimension biomes, found {len(biomes)}")
    if errors:
        print("\n".join("ERROR: " + e for e in errors))
        return 1
    print(f"Content factory check passed: {len(files)} JSON files, {len(biomes)} biomes.")
    return 0


def generate_item(item_id, name, ingredient, texture):
    if not re.fullmatch(r"[a-z0-9_]+", item_id):
        raise ValueError("item id must contain lowercase letters, digits and underscores")
    if not re.fullmatch(r"[a-z0-9_]+:[a-z0-9_]+", ingredient):
        raise ValueError("ingredient must be a namespaced item id")
    model = RES / "assets" / NS / "models" / "item" / f"{item_id}.json"
    recipe = RES / "data" / NS / "recipe" / f"{item_id}.json"
    if model.exists() or recipe.exists():
        raise FileExistsError(f"Refusing to overwrite existing content for {item_id}")
    lang = json.loads(LANG.read_text(encoding="utf-8")) if LANG.exists() else {}
    key = f"item.{NS}.{item_id}"
    if key in lang:
        raise FileExistsError(f"Translation already exists: {key}")
    model.parent.mkdir(parents=True, exist_ok=True)
    recipe.parent.mkdir(parents=True, exist_ok=True)
    model.write_text(json.dumps({
        "parent": "minecraft:item/generated",
        "textures": {"layer0": f"minecraft:item/{texture}"}
    }, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    recipe.write_text(json.dumps({
        "type": "minecraft:crafting_shaped",
        "category": "misc",
        "pattern": [" I ", " I ", " S "],
        "key": {
            "I": {"item": ingredient},
            "S": {"item": "minecraft:stick"}
        },
        "result": {"id": f"{NS}:{item_id}", "count": 1}
    }, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    lang[key] = name
    LANG.parent.mkdir(parents=True, exist_ok=True)
    LANG.write_text(json.dumps(lang, ensure_ascii=False, indent=2) + "\n", encoding="utf-8")
    print(f"Generated item bundle: {item_id} (model, recipe, ru_ru translation).")
    print("Remember: register the Java Item and provide a matching texture before building.")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true", help="validate generated resource JSON and biome inventory")
    parser.add_argument("--item", metavar="ID", help="generate a basic craftable item bundle")
    parser.add_argument("--name", help="Russian display name")
    parser.add_argument("--ingredient", help="namespaced crafting ingredient")
    parser.add_argument("--texture", default="iron_ingot", help="vanilla item texture name (default: iron_ingot)")
    args = parser.parse_args()
    try:
        if args.item:
            if not args.name or not args.ingredient:
                parser.error("--item requires --name and --ingredient")
            generate_item(args.item, args.name, args.ingredient, args.texture)
            return 0
        if args.check:
            return check()
        parser.print_help()
        return 0
    except (ValueError, FileExistsError, json.JSONDecodeError) as exc:
        print(f"ERROR: {exc}", file=sys.stderr)
        return 1


if __name__ == "__main__":
    raise SystemExit(main())
