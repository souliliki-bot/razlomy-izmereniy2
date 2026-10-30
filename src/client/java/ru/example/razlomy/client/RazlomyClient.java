package ru.example.razlomy.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.HuskRenderer;
import ru.example.razlomy.entity.GlassDesertEntityTypes;

public final class RazlomyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(GlassDesertEntityTypes.SAND_HUNTER, HuskRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SOLAR_SCORPION, HuskRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GLASS_WANDERER, HuskRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.GOLDEN_SAND_GOLEM, HuskRenderer::new);
        EntityRendererRegistry.register(GlassDesertEntityTypes.SUN_EATER, HuskRenderer::new);
    }
}