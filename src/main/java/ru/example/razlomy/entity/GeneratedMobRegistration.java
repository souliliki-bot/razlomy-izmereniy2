package ru.example.razlomy.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Husk;

/**
 * Shared registration helper for generated Husk-based mobs.
 * Call registerHusk from the mod initializer before the entity is used.
 */
public final class GeneratedMobRegistration {
    public static <T extends Husk> EntityType<T> registerHusk(
            String modId,
            String id,
            EntityType.EntityFactory<T> factory,
            float width,
            float height,
            double health,
            double damage,
            double speed) {
        EntityType<T> type = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            ResourceLocation.fromNamespaceAndPath(modId, id),
            EntityType.Builder.of(factory, MobCategory.MONSTER)
                .sized(width, height)
                .build(id)
        );

        AttributeSupplier attributes = Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, health)
            .add(Attributes.ATTACK_DAMAGE, damage)
            .add(Attributes.MOVEMENT_SPEED, speed)
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .build();
        FabricDefaultAttributeRegistry.register(type, attributes);
        return type;
    }

    private GeneratedMobRegistration() {}
}
