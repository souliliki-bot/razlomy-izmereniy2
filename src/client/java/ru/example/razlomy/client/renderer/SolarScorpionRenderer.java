package ru.example.razlomy.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import ru.example.razlomy.client.model.SolarScorpionModel;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class SolarScorpionRenderer extends MobRenderer<GlassDesertMobs.SolarScorpion, SolarScorpionModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "solar_scorpion"), "main");

    public SolarScorpionRenderer(EntityRendererProvider.Context context) {
        super(context, new SolarScorpionModel(context.bakeLayer(LAYER)), 0.35F);
    }

    @Override
    public ResourceLocation getTextureLocation(GlassDesertMobs.SolarScorpion entity) {
        return ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy",
            "textures/entity/glass_desert/solar_scorpion.png");
    }
}
