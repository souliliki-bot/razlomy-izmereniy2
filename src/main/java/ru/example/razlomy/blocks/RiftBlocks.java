package ru.example.razlomy.blocks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.core.Registry;

public class RiftBlocks {

    public static final Block RIFT_DEBRIS = register(
        "rift_debris",
        new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.DEEPSLATE)
            .strength(3.0F, 6.0F)
            .sound(SoundType.DEEPSLATE))
    );

    public static final Block COSMIC_CRYSTAL = register(
        "cosmic_crystal",
        new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_PURPLE)
            .strength(2.0F, 4.0F)
            .sound(SoundType.AMETHYST)
            .lightLevel(state -> 10))
    );

    public static final Block VOID_CORE = register(
        "void_core",
        new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLACK)
            .strength(5.0F, 8.0F)
            .sound(SoundType.AMETHYST)
            .lightLevel(state -> 7))
    );

    public static final Block STAR_STONE = register(
        "star_stone",
        new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BLUE)
            .strength(3.0F, 6.0F)
            .sound(SoundType.STONE)
            .lightLevel(state -> 5))
    );

    public static final Block SOLAR_ORE = register(
        "solar_ore",
        new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_YELLOW)
            .strength(4.0F, 5.0F)
            .requiresCorrectToolForDrops()
            .sound(SoundType.STONE)
            .lightLevel(state -> 3))
    );

    private static Block register(String name, Block block) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            "razlomy_izmereniy", name
        );

        Registry.register(BuiltInRegistries.BLOCK, id, block);

        Registry.register(
            BuiltInRegistries.ITEM,
            id,
            new BlockItem(block, new Item.Properties())
        );

        return block;
    }

    public static void initialize() {
    }
}
