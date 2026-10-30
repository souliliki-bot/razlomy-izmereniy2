package ru.example.razlomy.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import ru.example.razlomy.client.model.GlassWandererModel;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class GlassWandererRenderer extends MobRenderer<GlassDesertMobs.GlassWanderer, GlassWandererModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "glass_wanderer"), "main");

    public GlassWandererRenderer(EntityRendererProvider.Context context) {
        super(context, new GlassWandererModel(context.bakeLayer(LAYER)), 0.45F);
    }

    @Override
    public ResourceLocation getTextureLocation(GlassDesertMobs.GlassWanderer entity) {
        return ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy",
            "textures/entity/glass_desert/glass_wanderer.png");
    }
}
