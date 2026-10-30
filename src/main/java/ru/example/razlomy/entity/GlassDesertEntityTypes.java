package ru.example.razlomy.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Husk;
import ru.example.razlomy.items.RiftItems;
import ru.example.razlomy.entity.generated.FrostStalker;

/** Регистрация пяти существ Стеклянной пустыни с отдельными боевыми профилями. */
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

    public static final EntityType<FrostStalker> FROST_STALKER = GeneratedMobRegistration.registerHusk(
        MOD_ID, "frost_stalker", FrostStalker::new, 0.65F, 1.95F, 32.0, 6.0, 0.27D);

    private static <T extends net.minecraft.world.entity.Entity> EntityType<T> register(String id, EntityType<T> type) {
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, ResourceLocation.fromNamespaceAndPath(MOD_ID, id), type);
    }

    public static void initialize() {
        // Охотник быстро преследует цель и наносит сильные одиночные удары.
        FabricDefaultAttributeRegistry.register(SAND_HUNTER, Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.ATTACK_DAMAGE, 7.0)
            .add(Attributes.MOVEMENT_SPEED, 0.32)
            .add(Attributes.FOLLOW_RANGE, 40.0)
            .build());

        // Скорпион хрупкий, но очень быстрый и имеет повышенный отбрасывающий удар.
        FabricDefaultAttributeRegistry.register(SOLAR_SCORPION, Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, 18.0)
            .add(Attributes.ATTACK_DAMAGE, 4.0)
            .add(Attributes.MOVEMENT_SPEED, 0.38)
            .add(Attributes.ATTACK_KNOCKBACK, 1.8)
            .add(Attributes.FOLLOW_RANGE, 28.0)
            .build());

        // Странник медленнее, зато выдерживает больше ударов и защищён стеклянным панцирем.
        FabricDefaultAttributeRegistry.register(GLASS_WANDERER, Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, 42.0)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.MOVEMENT_SPEED, 0.22)
            .add(Attributes.ARMOR, 8.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.45)
            .build());

        // Голем — тяжёлый страж с высокой бронёй и устойчивостью к отбрасыванию.
        FabricDefaultAttributeRegistry.register(GOLDEN_SAND_GOLEM, Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, 90.0)
            .add(Attributes.ATTACK_DAMAGE, 13.0)
            .add(Attributes.MOVEMENT_SPEED, 0.19)
            .add(Attributes.ARMOR, 12.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
            .add(Attributes.FOLLOW_RANGE, 32.0)
            .build());

        // Финальный хищник измерения: огромный запас здоровья и дальнее преследование.
        FabricDefaultAttributeRegistry.register(SUN_EATER, Husk.createAttributes()
            .add(Attributes.MAX_HEALTH, 160.0)
            .add(Attributes.ATTACK_DAMAGE, 19.0)
            .add(Attributes.MOVEMENT_SPEED, 0.25)
            .add(Attributes.ARMOR, 10.0)
            .add(Attributes.KNOCKBACK_RESISTANCE, 0.65)
            .add(Attributes.FOLLOW_RANGE, 48.0)
            .build());
    }

    private GlassDesertEntityTypes() {}
}
