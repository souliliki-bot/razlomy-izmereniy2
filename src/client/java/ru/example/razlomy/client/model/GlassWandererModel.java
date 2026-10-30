package ru.example.razlomy.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.util.Mth;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class GlassWandererModel extends HierarchicalModel<GlassDesertMobs.GlassWanderer> {
    private final ModelPart root;
    private final ModelPart core;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart shardLeft;
    private final ModelPart shardRight;

    public GlassWandererModel(ModelPart root) {
        this.root = root;
        this.core = root.getChild("core");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
        this.shardLeft = root.getChild("shard_left");
        this.shardRight = root.getChild("shard_right");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("core", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 15.0F, 6.0F)
            .texOffs(28, 0).addBox(-3.0F, -15.0F, -2.5F, 6.0F, 6.0F, 5.0F)
            .texOffs(0, 22).addBox(-3.0F, 6.0F, -2.0F, 6.0F, 4.0F, 4.0F),
            PartPose.ZERO);
        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
            .texOffs(0, 32).addBox(-1.0F, -1.0F, -1.5F, 3.0F, 15.0F, 3.0F),
            PartPose.offset(4.0F, -5.0F, 0.0F));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create()
            .texOffs(12, 32).addBox(-2.0F, -1.0F, -1.5F, 3.0F, 15.0F, 3.0F),
            PartPose.offset(-4.0F, -5.0F, 0.0F));
        root.addOrReplaceChild("shard_left", CubeListBuilder.create()
            .texOffs(24, 32).addBox(-1.0F, -2.0F, -1.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-0.15F)),
            PartPose.offset(4.0F, -8.0F, 0.0F));
        root.addOrReplaceChild("shard_right", CubeListBuilder.create()
            .texOffs(44, 32).addBox(-4.0F, -2.0F, -1.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(-0.15F)),
            PartPose.offset(-4.0F, -8.0F, 0.0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    @Override
    public ModelPart root() { return root; }

    @Override
    public void setupAnim(GlassDesertMobs.GlassWanderer entity, float limbSwing, float limbSwingAmount,
                          float ageInTicks, float netHeadYaw, float headPitch) {
        float wave = Mth.sin(ageInTicks * 0.08F);
        core.y = wave * 0.8F;
        leftArm.xRot = -0.18F + Mth.sin(ageInTicks * 0.06F) * 0.12F;
        rightArm.xRot = -0.18F - Mth.sin(ageInTicks * 0.06F) * 0.12F;
        leftArm.zRot = 0.22F;
        rightArm.zRot = -0.22F;
        shardLeft.yRot = ageInTicks * 0.035F;
        shardRight.yRot = -ageInTicks * 0.035F;
        shardLeft.xRot = wave * 0.12F;
        shardRight.xRot = -wave * 0.12F;
    }
}
