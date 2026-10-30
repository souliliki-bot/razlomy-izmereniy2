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



def generate_armor_set(set_id, name, ingredient):
    if not re.fullmatch(r"[a-z0-9_]+", set_id):
        raise ValueError("armor set id must contain lowercase letters, digits and underscores")
    if not re.fullmatch(r"[a-z0-9_]+:[a-z0-9_]+", ingredient):
        raise ValueError("ingredient must be a namespaced item id")
    pieces = {
        "helmet": ("шлем", "diamond_helmet", ["MMM", "M M"]),
        "chestplate": ("нагрудник", "diamond_chestplate", ["M M", "MMM", "MMM"]),
        "leggings": ("поножи", "diamond_leggings", ["MMM", "M M", "M M"]),
        "boots": ("ботинки", "diamond_boots", ["M M", "M M"]),
    }
    lang = json.loads(LANG.read_text(encoding="utf-8")) if LANG.exists() else {}
    outputs = []
    for part, (part_name, texture, pattern) in pieces.items():
        item_id = f"{set_id}_{part}"
        model = RES / "assets" / NS / "models" / "item" / f"{item_id}.json"
        recipe = RES / "data" / NS / "recipe" / f"{item_id}.json"
        key = f"item.{NS}.{item_id}"
        if model.exists() or recipe.exists() or key in lang:
            raise FileExistsError(f"Refusing to overwrite existing armor piece: {item_id}")
        outputs.append((item_id, part_name, texture, pattern, model, recipe, key))
    # Validate all destinations first, then write the complete set.
    for item_id, part_name, texture, pattern, model, recipe, key in outputs:
        model.parent.mkdir(parents=True, exist_ok=True)
        recipe.parent.mkdir(parents=True, exist_ok=True)
        model.write_text(json.dumps({
            "parent": "minecraft:item/generated",
            "textures": {"layer0": f"minecraft:item/{texture}"}
        }, ensure_ascii=False, indent=2) + "\\n", encoding="utf-8")
        keys = {"M": {"item": ingredient}}
        recipe.write_text(json.dumps({
            "type": "minecraft:crafting_shaped",
            "category": "equipment",
            "pattern": pattern,
            "key": keys,
            "result": {"id": f"{NS}:{item_id}", "count": 1}
        }, ensure_ascii=False, indent=2) + "\\n", encoding="utf-8")
        lang[key] = f"{name} {part_name}"
    LANG.parent.mkdir(parents=True, exist_ok=True)
    LANG.write_text(json.dumps(lang, ensure_ascii=False, indent=2) + "\\n", encoding="utf-8")
    print(f"Generated armor set {set_id}: helmet, chestplate, leggings, boots.")
    print("Remember: register all four Java ArmorItems and provide matching armor layer textures.")


def generate_mob_java(mob_id, class_name, speed):
    if not re.fullmatch(r"[a-z0-9_]+", mob_id):
        raise ValueError("mob id must contain lowercase letters, digits and underscores")
    if not re.fullmatch(r"[A-Z][A-Za-z0-9]*", class_name):
        raise ValueError("class name must be PascalCase and contain only letters/digits")
    if not (0.05 <= speed <= 2.0):
        raise ValueError("movement speed must be between 0.05 and 2.0")
    package_dir = ROOT / "src/main/java/ru/example/razlomy/entity/generated"
    target = package_dir / f"{class_name}.java"
    if target.exists():
        raise FileExistsError(f"Refusing to overwrite {target.relative_to(ROOT)}")
    source = f"""package ru.example.razlomy.entity.generated;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.Level;

/**
 * Generated starting point for {mob_id}.
 * Register an EntityType for this class and add its attributes in your entity registry.
 */
public final class {class_name} extends Husk {{
    public {class_name}(EntityType<? extends Husk> type, Level level) {{
        super(type, level);
    }}

    @Override
    protected void registerGoals() {{
        super.registerGoals();
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, {speed:.2f}D, true));
    }}
}}
"""
    package_dir.mkdir(parents=True, exist_ok=True)
    target.write_text(source, encoding="utf-8")
    print(f"Generated Java mob scaffold: {target.relative_to(ROOT)}")
    print("Next: register its EntityType and attributes, then add client renderer/model/texture.")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--check", action="store_true", help="validate generated resource JSON and biome inventory")
    parser.add_argument("--item", metavar="ID", help="generate a basic craftable item bundle")
    parser.add_argument("--armor-set", metavar="ID", help="generate a four-piece craftable armor resource set")
    parser.add_argument("--name", help="Russian display name")
    parser.add_argument("--ingredient", help="namespaced crafting ingredient")
    parser.add_argument("--texture", default="iron_ingot", help="vanilla item texture name (default: iron_ingot)")
    parser.add_argument("--mob-java", metavar="ID", help="generate a Java Husk-based mob class scaffold")
    parser.add_argument("--class-name", help="PascalCase Java class name for --mob-java")
    parser.add_argument("--speed", type=float, default=1.0, help="melee movement speed for --mob-java (default: 1.0)")
    args = parser.parse_args()
    try:
        if args.mob_java:
            if not args.class_name:
                parser.error("--mob-java requires --class-name")
            generate_mob_java(args.mob_java, args.class_name, args.speed)
            return 0
        if args.armor_set:
            if not args.name or not args.ingredient:
                parser.error("--armor-set requires --name and --ingredient")
            generate_armor_set(args.armor_set, args.name, args.ingredient)
            return 0
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
