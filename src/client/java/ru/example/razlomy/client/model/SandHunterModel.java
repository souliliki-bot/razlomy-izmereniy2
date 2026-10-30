package ru.example.razlomy.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class SandHunterModel extends HierarchicalModel<GlassDesertMobs.SandHunter> {
    private final ModelPart root, body, head, leftArm, rightArm, leftLeg, rightLeg, jaw;
    public SandHunterModel(ModelPart root) {
        this.root=root; body=root.getChild("body"); head=root.getChild("head");
        leftArm=root.getChild("left_arm"); rightArm=root.getChild("right_arm");
        leftLeg=root.getChild("left_leg"); rightLeg=root.getChild("right_leg"); jaw=head.getChild("jaw");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition root=mesh.getRoot();
        root.addOrReplaceChild("body",CubeListBuilder.create().texOffs(0,16).addBox(-4,-6,-2,8,12,4)
            .texOffs(24,16).addBox(-5,-5,-2.5F,10,5,5),PartPose.offset(0,0,0));
        PartDefinition head=root.addOrReplaceChild("head",CubeListBuilder.create()
            .texOffs(0,0).addBox(-4,-8,-4,8,8,8),PartPose.offset(0,-7,0));
        head.addOrReplaceChild("jaw",CubeListBuilder.create().texOffs(32,0).addBox(-3,0,-4,6,2,5),PartPose.ZERO);
        root.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(40,16).addBox(-2,-2,-2,4,12,4),
            PartPose.offset(5,-4,0));
        root.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(40,16).addBox(-2,-2,-2,4,12,4),
            PartPose.offset(-5,-4,0));
        root.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(0,40).addBox(-2,0,-2,4,10,4),
            PartPose.offset(2,6,0));
        root.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(0,40).addBox(-2,0,-2,4,10,4),
            PartPose.offset(-2,6,0));
        return LayerDefinition.create(mesh,64,64);
    }
    @Override public ModelPart root(){return root;}
    @Override public void setupAnim(GlassDesertMobs.SandHunter entity,float swing,float amount,float age,float yaw,float pitch) {
        head.yRot=yaw*Mth.DEG_TO_RAD; head.xRot=pitch*Mth.DEG_TO_RAD;
        leftArm.xRot=Mth.cos(swing*0.6662F+Mth.PI)*1.4F*amount;
        rightArm.xRot=Mth.cos(swing*0.6662F)*1.4F*amount;
        leftLeg.xRot=Mth.cos(swing*0.6662F)*1.2F*amount;
        rightLeg.xRot=Mth.cos(swing*0.6662F+Mth.PI)*1.2F*amount;
        jaw.xRot=0.12F+Mth.sin(age*0.15F)*0.08F;
    }
}