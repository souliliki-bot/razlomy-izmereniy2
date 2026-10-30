package ru.example.razlomy.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class SolarScorpionModel extends HierarchicalModel<GlassDesertMobs.SolarScorpion> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart clawLeft;
    private final ModelPart clawRight;
    private final ModelPart[] legs = new ModelPart[6];

    public SolarScorpionModel(ModelPart root) {
        this.root = root;
        body = root.getChild("body");
        tail = root.getChild("tail");
        clawLeft = root.getChild("claw_left");
        clawRight = root.getChild("claw_right");
        for (int i = 0; i < legs.length; i++) legs[i] = root.getChild("leg_" + i);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-5, -3, -7, 10, 6, 14)
            .texOffs(0, 22).addBox(-3, -5, -3, 6, 3, 7), PartPose.ZERO);
        root.addOrReplaceChild("tail", CubeListBuilder.create()
            .texOffs(32, 0).addBox(-1.5F, -1.5F, -1.5F, 3, 9, 3)
            .texOffs(44, 0).addBox(-2.5F, 6, -2.5F, 5, 5, 5), PartPose.offset(0, -1, 6));
        root.addOrReplaceChild("claw_left", CubeListBuilder.create()
            .texOffs(0, 36).addBox(-1, -1, -5, 3, 3, 7), PartPose.offset(4, 0, -5));
        root.addOrReplaceChild("claw_right", CubeListBuilder.create()
            .texOffs(20, 36).addBox(-2, -1, -5, 3, 3, 7), PartPose.offset(-4, 0, -5));
        for (int i = 0; i < 6; i++) {
            float side = (i % 2 == 0) ? 1.0F : -1.0F;
            float z = -5.0F + (i / 2) * 5.0F;
            root.addOrReplaceChild("leg_" + i, CubeListBuilder.create()
                .texOffs(40, 32).addBox(-1, -1, -1, 2, 2, 8),
                PartPose.offset(side * 4.0F, 1.0F, z));
        }
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override public ModelPart root() { return root; }

    @Override
    public void setupAnim(GlassDesertMobs.SolarScorpion entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        tail.xRot = -0.65F + Mth.sin(ageInTicks * 0.12F) * 0.18F;
        tail.yRot = Mth.sin(ageInTicks * 0.08F) * 0.12F;
        clawLeft.yRot = 0.12F + Mth.sin(ageInTicks * 0.15F) * 0.1F;
        clawRight.yRot = -0.12F - Mth.sin(ageInTicks * 0.15F) * 0.1F;
        for (int i = 0; i < legs.length; i++) {
            legs[i].zRot = Mth.cos(limbSwing * 0.8F + (i % 2) * Mth.PI) * limbSwingAmount * 0.45F;
        }
    }
}
