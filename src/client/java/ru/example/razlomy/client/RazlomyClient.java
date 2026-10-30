package ru.example.razlomy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Husk;
import ru.example.razlomy.entity.GlassDesertEntityTypes;

/**
 * Отдельные визуальные профили существ Стеклянной пустыни.
 * На этом этапе используются совместимые ванильные текстуры; индивидуальные
 * геометрические модели будут добавлены следующим проходом.
 */
public final class RazlomyClient implements ClientModInitializer {
    private static final ResourceLocation HUNTER_TEXTURE =
        ResourceLocation.withDefaultNamespace("textures/entity/husk/husk.png");
    private static final ResourceLocation SCORPION_TEXTURE =
        ResourceLocation.withDefaultNamespace("textures/entity/silverfish.png");
    private static final ResourceLocation WANDERER_TEXTURE =
        ResourceLocation.withDefaultNamespace("textures/entity/zombie/drowned.png");
    private static final ResourceLocation GOLEM_TEXTURE =
        ResourceLocation.withDefaultNamespace("textures/entity/iron_golem/iron_golem.png");
    private static final ResourceLocation SUN_EATER_TEXTURE =
        ResourceLocation.withDefaultNamespace("textures/entity/skeleton/wither_skeleton.png");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(GlassDesertEntityTypes.SAND_HUNTER, SandHunterRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SOLAR_SCORPION, SolarScorpionRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GLASS_WANDERER, GlassWandererRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GOLDEN_SAND_GOLEM, GoldenSandGolemRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SUN_EATER, SunEaterRenderer::new);
    }

    private static final class SandHunterRenderer extends HuskRenderer {
        SandHunterRenderer(EntityRendererProvider.Context context) { super(context); }
        @Override public ResourceLocation getTextureLocation(Husk entity) { return HUNTER_TEXTURE; }
    }

    private static final class SolarScorpionRenderer extends HuskRenderer {
        SolarScorpionRenderer(EntityRendererProvider.Context context) { super(context); }
        @Override public ResourceLocation getTextureLocation(Husk entity) { return SCORPION_TEXTURE; }
    }

    private static final class GlassWandererRenderer extends HuskRenderer {
        GlassWandererRenderer(EntityRendererProvider.Context context) { super(context); }
        @Override public ResourceLocation getTextureLocation(Husk entity) { return WANDERER_TEXTURE; }
    }

    private static final class GoldenSandGolemRenderer extends HuskRenderer {
        GoldenSandGolemRenderer(EntityRendererProvider.Context context) { super(context); }
        @Override public ResourceLocation getTextureLocation(Husk entity) { return GOLEM_TEXTURE; }
    }

    private static final class SunEaterRenderer extends HuskRenderer {
        SunEaterRenderer(EntityRendererProvider.Context context) { super(context); }
        @Override public ResourceLocation getTextureLocation(Husk entity) { return SUN_EATER_TEXTURE; }
    }
}
