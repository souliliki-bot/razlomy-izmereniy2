package ru.example.razlomy.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

/** Солнечная экипировка Стеклянной пустыни. */
public final class SolarEquipment {
    private static final String MOD_ID = RiftItems.MOD_ID;

    public static final Item HELMET = register("solar_helmet",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.HELMET,
            new Item.Properties().fireResistant()));
    public static final Item CHESTPLATE = register("solar_chestplate",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE,
            new Item.Properties().fireResistant()));
    public static final Item LEGGINGS = register("solar_leggings",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.LEGGINGS,
            new Item.Properties().fireResistant()));
    public static final Item BOOTS = register("solar_boots",
        new ArmorItem(ArmorMaterials.DIAMOND, ArmorItem.Type.BOOTS,
            new Item.Properties().fireResistant()));

    public static final Item SOLAR_SWORD = register("solar_sword",
        new Item(new Item.Properties().durability(900).fireResistant().attributes(
            combatAttributes("solar_sword", 8.0, -2.4))));
    public static final Item SOLAR_PICKAXE = register("solar_pickaxe",
        new Item(new Item.Properties().durability(1050).fireResistant().attributes(
            combatAttributes("solar_pickaxe", 5.0, -2.8))));
    public static final Item SOLAR_AXE = register("solar_axe",
        new Item(new Item.Properties().durability(1050).fireResistant().attributes(
            combatAttributes("solar_axe", 9.0, -3.0))));

    private static ItemAttributeModifiers combatAttributes(String id, double damage, double speed) {
        return ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, id + "_damage"),
                    damage, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED,
                new AttributeModifier(ResourceLocation.fromNamespaceAndPath(MOD_ID, id + "_speed"),
                    speed, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND)
            .build();
    }

    private static Item register(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, name), item);
    }

    public static void initialize() {}
    private SolarEquipment() {}
}
