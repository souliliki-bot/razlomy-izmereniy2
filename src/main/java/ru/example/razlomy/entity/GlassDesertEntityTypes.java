package ru.example.razlomy.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import ru.example.razlomy.items.RiftItems;

public final class GlassDesertEntityTypes {
    private static final String MOD_ID = RiftItems.MOD_ID;
    public static final EntityType<GlassDesertMobs.SandHunter> SAND_HUNTER = register("sand_hunter",
        EntityType.Builder.of(GlassDesertMobs.SandHunter::new, MobCategory.MONSTER).sized(0.6F, 1.95F).build("sand_hunter"));
    public static final EntityType<GlassDesertMobs.SolarScorpion> SOLAR_SCORPION = register("solar_scorpion",
        EntityType.Builder.of(GlassDesertMobs.SolarScorpion::new, MobCategory.MONSTER).sized(0.8F, 0.6F).build("solar_scorpion"));
    public static final EntityType<GlassDesertMobs.GlassWanderer> GLASS_WANDERER = register("glass_wanderer",
        EntityType.Builder.of(GlassDesertMobs.GlassWanderer::new, MobCategory.MONSTER).sized(0.7F, 2.0F).build("glass_wanderer"));
    public static final EntityType<GlassDesertMobs.GoldenSandGolem> GOLDEN_SAND_GOLEM = register("golden_sand_golem",
        EntityType.Builder.of(GlassDesertMobs.GoldenSandGolem::new, MobCategory.MONSTER).sized(1.4F, 2.7F).build("golden_sand_golem"));
    public static final EntityType<GlassDesertMobs.SunEater> SUN_EATER = register("sun_eater",
        EntityType.Builder.of(GlassDesertMobs.SunEater::new, MobCategory.MONSTER).sized(1.8F, 3.2F).build("sun_eater"));

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String id, EntityType<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, id), type);
    }

    public static void initialize() {
        var attributes = Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, 26.0)
            .add(Attributes.ATTACK_DAMAGE, 5.0)
            .add(Attributes.MOVEMENT_SPEED, 0.27)
            .build();
        FabricDefaultAttributeRegistry.register(SAND_HUNTER, attributes);
        FabricDefaultAttributeRegistry.register(SOLAR_SCORPION, attributes);
        FabricDefaultAttributeRegistry.register(GLASS_WANDERER, attributes);
        FabricDefaultAttributeRegistry.register(GOLDEN_SAND_GOLEM,
            Husk.createAttributes().add(Attributes.MAX_HEALTH, 70.0).add(Attributes.ATTACK_DAMAGE, 12.0).add(Attributes.MOVEMENT_SPEED, 0.20).build());
        FabricDefaultAttributeRegistry.register(SUN_EATER,
            Husk.createAttributes().add(Attributes.MAX_HEALTH, 140.0).add(Attributes.ATTACK_DAMAGE, 18.0).add(Attributes.MOVEMENT_SPEED, 0.24).build());
    }
    private GlassDesertEntityTypes() {}
}