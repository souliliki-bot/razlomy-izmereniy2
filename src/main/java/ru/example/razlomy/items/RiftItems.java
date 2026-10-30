package ru.example.razlomy.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public final class RiftItems {
    public static final String MOD_ID = "razlomy_izmereniy";

    public static final Item RIFT_CRYSTAL = register(
        "rift_crystal",
        new Item.Properties()
    );

    public static final Item RIFT_SWORD = register(
        "rift_sword",
        new Item.Properties()
            .durability(1561)
            .attributes(
                ItemAttributeModifiers.builder()
                    .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(
                                MOD_ID, "rift_sword_damage"
                            ),
                            7.0,
                            AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                    )
                    .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                            ResourceLocation.fromNamespaceAndPath(
                                MOD_ID, "rift_sword_speed"
                            ),
                            -2.4,
                            AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                    )
                    .build()
            )
    );

    public static final Item PORTAL_GUN = register(
        "portal_gun",
        new PortalGunItem(new Item.Properties().stacksTo(1).durability(512))
    );

    public static final Item LOLOLOSHKA_RELIC = register(
        "lololoshka_relic",
        new Item.Properties().fireResistant()
    );

    public static final Item SCP_SUS6_FILE = register(
        "scp_sus6_file",
        new Item.Properties().stacksTo(1)
    );

    private static Item register(String name, Item.Properties properties) {
        return Registry.register(
            BuiltInRegistries.ITEM,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
            new Item(properties)
        );
    }

    public static void initialize() {}

    private RiftItems() {}
}
