package ru.example.razlomy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import ru.example.razlomy.client.model.GlassWandererModel;
import ru.example.razlomy.client.model.SolarScorpionModel;
import ru.example.razlomy.client.model.GoldenSandGolemModel;
import ru.example.razlomy.client.model.SandHunterModel;
import ru.example.razlomy.client.model.SunEaterModel;
import ru.example.razlomy.client.renderer.SolarScorpionRenderer;
import ru.example.razlomy.client.renderer.GoldenSandGolemRenderer;
import ru.example.razlomy.client.renderer.SandHunterRenderer;
import ru.example.razlomy.client.renderer.SunEaterRenderer;
import ru.example.razlomy.client.renderer.GlassWandererRenderer;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Husk;
import ru.example.razlomy.entity.GlassDesertEntityTypes;

/**
 * Клиентские рендереры существ Стеклянной пустыни.
 */
public final class RazlomyClient implements ClientModInitializer {
    private static final ResourceLocation HUNTER_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/sand_hunter.png");
    private static final ResourceLocation SCORPION_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/solar_scorpion.png");
    private static final ResourceLocation WANDERER_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/glass_wanderer.png");
    private static final ResourceLocation GOLEM_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/golden_sand_golem.png");
    private static final ResourceLocation SUN_EATER_TEXTURE =
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "textures/entity/glass_desert/sun_eater.png");

    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(SandHunterRenderer.LAYER, SandHunterModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SAND_HUNTER, SandHunterRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SolarScorpionRenderer.LAYER, SolarScorpionModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SOLAR_SCORPION, SolarScorpionRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GlassWandererRenderer.LAYER, GlassWandererModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GLASS_WANDERER, GlassWandererRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(GoldenSandGolemRenderer.LAYER, GoldenSandGolemModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GOLDEN_SAND_GOLEM, GoldenSandGolemRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(SunEaterRenderer.LAYER, SunEaterModel::createBodyLayer);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SUN_EATER, SunEaterRenderer::new);
    }


}
