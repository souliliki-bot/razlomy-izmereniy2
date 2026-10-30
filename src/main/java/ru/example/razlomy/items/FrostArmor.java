package ru.example.razlomy.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;

/** Полный комплект брони Ледяной Короны. */
public final class FrostArmor {
    private static final String MOD_ID = RiftItems.MOD_ID;

    public static final Item HELMET = register("frost_helmet",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET,
            new Item.Properties().fireResistant()));
    public static final Item CHESTPLATE = register("frost_chestplate",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE,
            new Item.Properties().fireResistant()));
    public static final Item LEGGINGS = register("frost_leggings",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.LEGGINGS,
            new Item.Properties().fireResistant()));
    public static final Item BOOTS = register("frost_boots",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS,
            new Item.Properties().fireResistant()));

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, name), item);
    }

    public static void initialize() {}
    private FrostArmor() {}
}
