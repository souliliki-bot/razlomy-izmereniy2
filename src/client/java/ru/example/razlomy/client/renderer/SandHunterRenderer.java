package ru.example.razlomy.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.resources.ResourceLocation;
import ru.example.razlomy.client.model.SandHunterModel;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class SandHunterRenderer extends MobRenderer<GlassDesertMobs.SandHunter, SandHunterModel> {
    public static final ModelLayerLocation LAYER=new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy","sand_hunter"),"main");
    public SandHunterRenderer(EntityRendererProvider.Context context) {
        super(context,new SandHunterModel(context.bakeLayer(LAYER)),0.45F);
    }
    @Override public ResourceLocation getTextureLocation(GlassDesertMobs.SandHunter entity) {
        return ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy","textures/entity/glass_desert/sand_hunter.png");
    }
}