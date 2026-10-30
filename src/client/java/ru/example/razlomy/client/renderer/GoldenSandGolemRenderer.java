package ru.example.razlomy.client.renderer;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import ru.example.razlomy.client.model.GoldenSandGolemModel;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class GoldenSandGolemRenderer extends MobRenderer<GlassDesertMobs.GoldenSandGolem, GoldenSandGolemModel> {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
        ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy", "golden_sand_golem"), "main");
    public GoldenSandGolemRenderer(EntityRendererProvider.Context context) {
        super(context, new GoldenSandGolemModel(context.bakeLayer(LAYER)), 0.8F);
    }
    @Override public ResourceLocation getTextureLocation(GlassDesertMobs.GoldenSandGolem entity) {
        return ResourceLocation.fromNamespaceAndPath("razlomy_izmereniy",
            "textures/entity/glass_desert/golden_sand_golem.png");
    }
}
