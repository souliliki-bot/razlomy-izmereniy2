#!/usr/bin/env python3
"""Offline structural checks for the Razlomy Izmereniy project."""
import json
import pathlib
import re
import unittest

ROOT = pathlib.Path(__file__).resolve().parents[1]
RES = ROOT / "src" / "main" / "resources"
DIMENSIONS = {
    "echo_limit", "rift_forge", "zero_sun_garden", "glass_desert",
    "silent_ocean", "storm_limit", "ice_crown", "mechanical_hive",
    "crimson_mire", "lunar_necropolis", "wild_rift", "chrono_archive",
}

class ProjectStructureTests(unittest.TestCase):
    def test_all_json_resources_parse(self):
        paths = list(RES.rglob("*.json"))
        self.assertGreaterEqual(len(paths), 12)
        for path in paths:
            with self.subTest(path=str(path.relative_to(ROOT))):
                json.loads(path.read_text(encoding="utf-8"))

    def test_exactly_twelve_custom_dimension_definitions(self):
        folder = RES / "data" / "razlomy_izmereniy" / "dimension"
        found = {p.stem for p in folder.glob("*.json")}
        self.assertEqual(found, DIMENSIONS)
        for name in DIMENSIONS:
            data = json.loads((folder / f"{name}.json").read_text(encoding="utf-8"))
            self.assertIn("type", data)
            self.assertIn("generator", data)
            self.assertEqual(data["generator"]["type"], "minecraft:noise")
            self.assertIn("settings", data["generator"])
            self.assertIn("biome_source", data["generator"])

    def test_every_dimension_has_a_command_route(self):
        source = (ROOT / "src/main/java/ru/example/razlomy/RazlomyIzmereniy.java").read_text(encoding="utf-8")
        for name in DIMENSIONS:
            with self.subTest(dimension=name):
                self.assertRegex(source, rf'travel\(c\.getSource\(\)\.getPlayerOrException\(\),\s*"{name}"')

    def test_mod_metadata_has_entrypoint(self):
        metadata = json.loads((RES / "fabric.mod.json").read_text(encoding="utf-8"))
        self.assertEqual(metadata["id"], "razlomy_izmereniy")
        self.assertIn("main", metadata["entrypoints"])
        self.assertTrue(metadata["entrypoints"]["main"])

if __name__ == "__main__":
    unittest.main(verbosity=2)
