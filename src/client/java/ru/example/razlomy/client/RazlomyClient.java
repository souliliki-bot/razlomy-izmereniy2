package ru.example.razlomy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.resources.ResourceLocation;
import ru.example.razlomy.client.model.GlassWandererModel;
import ru.example.razlomy.client.model.SolarScorpionModel;
import ru.example.razlomy.client.model.GoldenSandGolemModel;
import ru.example.razlomy.client.renderer.SolarScorpionRenderer;
import ru.example.razlomy.client.renderer.GoldenSandGolemRenderer;
import ru.example.razlomy.client.renderer.GlassWandererRenderer;
import ru.example.razlomy.client.renderer.FrostStalkerRenderer;
import ru.example.razlomy.entity.GlassDesertEntityTypes;

/**
 * Клиентские рендереры существ Стеклянной пустыни.
 */
public final class RazlomyClient implements ClientModInitializer {
    private static final ResourceLocation SCORPION_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/solar_scorpion.png");
    private static final ResourceLocation WANDERER_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/glass_wanderer.png");
    private static final ResourceLocation GOLEM_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/golden_sand_golem.png");

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(GlassDesertEntityTypes.FROST_STALKER, FrostStalkerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SolarScorpionRenderer.LAYER, SolarScorpionModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SOLAR_SCORPION, SolarScorpionRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GlassWandererRenderer.LAYER, GlassWandererModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GLASS_WANDERER, GlassWandererRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GoldenSandGolemRenderer.LAYER, GoldenSandGolemModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GOLDEN_SAND_GOLEM, GoldenSandGolemRenderer::new);
    }


}
