package ru.example.razlomy.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/** Материалы и оружие Ледяной Короны. */
public final class FrostItems {
    private static final String MOD_ID = RiftItems.MOD_ID;

    public static final Item FROST_SHARD = register("frost_shard",
        new Item.Properties());
    public static final Item FROST_INGOT = register("frost_ingot",
        new Item.Properties());
    public static final Item GLACIAL_HEART = register("glacial_heart",
        new Item.Properties().fireResistant());
    public static final Item FROST_BLADE = register("frost_blade",
        new Item.Properties().durability(780).attributes(
            ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE,
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "frost_blade_damage"),
                        6.0, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED,
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, "frost_blade_speed"),
                        -2.2, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build()));

    private static Item register(String name, Item.Properties properties) {
        return Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, name), new Item(properties));
    }

    public static void initialize() {}
    private FrostItems() {}
}
