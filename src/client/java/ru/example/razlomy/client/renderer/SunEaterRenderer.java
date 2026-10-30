package ru.example.razlomy.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import ru.example.razlomy.client.model.SunEaterModel;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class SunEaterRenderer extends MobRenderer<GlassDesertMobs.SunEater, SunEaterModel> {
    public static final ModelLayerLocation LAYER=new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy","sun_eater"),"main");
    public SunEaterRenderer(EntityRendererProvider.Context context) {
        super(context,new SunEaterModel(context.bakeLayer(LAYER)),0.55F);
    }
    @Override public ResourceLocation getTextureLocation(GlassDesertMobs.SunEater entity) {
        return ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy","textures/entity/glass_desert/sun_eater.png");
    }
}