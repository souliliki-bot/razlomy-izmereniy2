package ru.example.razlomy.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HuskRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Husk;

/** Отдельная ледяная текстура для Морозного охотника. */
public final class FrostStalkerRenderer extends HuskRenderer {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        "razlomy_izmereniy", "textures/entity/ice_crown/frost_stalker.png");

    public FrostStalkerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Husk entity) {
        return TEXTURE;
    }
}
