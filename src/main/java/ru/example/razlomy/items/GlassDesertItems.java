package ru.example.razlomy.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

/**
 * Уникальные трофеи и пища Стеклянной пустыни.
 * Мобы будут использовать эти предметы в своих таблицах добычи.
 */
public final class GlassDesertItems {
    private static final String MOD_ID = RiftItems.MOD_ID;

    public static final Item HUNTER_MEAT = register("hunter_meat",
        new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(5).saturationModifier(0.6F).meat().build()));

    public static final Item SCORPION_MEAT = register("scorpion_meat",
        new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(4).saturationModifier(0.5F).meat().build()));

    public static final Item SOLAR_STINGER = register("solar_stinger",
        new Item.Properties());

    public static final Item GLASS_FLESH = register("glass_flesh",
        new Item.Properties().food(new FoodProperties.Builder()
            .nutrition(3).saturationModifier(0.3F).alwaysEdible().build()));

    public static final Item GOLDEN_CORE = register("golden_core",
        new Item.Properties());

    public static final Item SUN_HEART = register("sun_heart",
        new Item.Properties().fireResistant());

    public static final Item SOLAR_INGOT = register("solar_ingot",
        new Item.Properties().fireResistant());

    private static Item register(String name, Item.Properties properties) {
        return Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
            new Item(properties));
    }

    public static void initialize() {}

    private GlassDesertItems() {}
}
