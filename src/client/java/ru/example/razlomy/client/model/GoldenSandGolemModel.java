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

public final class GoldenSandGolemModel extends HierarchicalModel<GlassDesertMobs.GoldenSandGolem> {
    private final ModelPart root, torso, head, leftArm, rightArm, leftLeg, rightLeg, crest;
    public GoldenSandGolemModel(ModelPart root) {
        this.root=root; torso=root.getChild("torso"); head=root.getChild("head");
        leftArm=root.getChild("left_arm"); rightArm=root.getChild("right_arm");
        leftLeg=root.getChild("left_leg"); rightLeg=root.getChild("right_leg"); crest=root.getChild("crest");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition root=mesh.getRoot();
        root.addOrReplaceChild("torso", CubeListBuilder.create()
            .texOffs(0,0).addBox(-7,-10,-4,14,18,8)
            .texOffs(0,27).addBox(-5,7,-3,10,5,6), PartPose.offset(0,-1,0));
        root.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(32,0).addBox(-4,-8,-4,8,8,8)
            .texOffs(32,17).addBox(-1,-4,-5,2,2,1), PartPose.offset(0,-12,0));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create()
            .texOffs(0,40).addBox(-3,-2,-3,6,16,6), PartPose.offset(9,-7,0));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create()
            .texOffs(24,40).addBox(-3,-2,-3,6,16,6), PartPose.offset(-9,-7,0));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create()
            .texOffs(48,40).addBox(-3,0,-3,6,10,6), PartPose.offset(4,7,0));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create()
            .texOffs(48,40).addBox(-3,0,-3,6,10,6), PartPose.offset(-4,7,0));
        root.addOrReplaceChild("crest", CubeListBuilder.create()
            .texOffs(48,20).addBox(-1,-8,-1,2,8,2), PartPose.offset(0,-19,0));
        return LayerDefinition.create(mesh,64,64);
    }
    @Override public ModelPart root(){return root;}
    @Override public void setupAnim(GlassDesertMobs.GoldenSandGolem entity,float limbSwing,float limbSwingAmount,
        float ageInTicks,float netHeadYaw,float headPitch) {
        head.yRot=netHeadYaw*Mth.DEG_TO_RAD; head.xRot=headPitch*Mth.DEG_TO_RAD;
        leftArm.xRot=Mth.cos(limbSwing*0.5F+Mth.PI)*limbSwingAmount*0.5F;
        rightArm.xRot=Mth.cos(limbSwing*0.5F)*limbSwingAmount*0.5F;
        leftLeg.xRot=Mth.cos(limbSwing*0.5F)*limbSwingAmount*0.65F;
        rightLeg.xRot=Mth.cos(limbSwing*0.5F+Mth.PI)*limbSwingAmount*0.65F;
        crest.zRot=Mth.sin(ageInTicks*0.06F)*0.08F;
    }
}
