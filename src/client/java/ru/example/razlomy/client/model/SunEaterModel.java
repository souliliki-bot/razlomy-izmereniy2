package ru.example.razlomy.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import ru.example.razlomy.entity.GlassDesertMobs;

public final class SunEaterModel extends HierarchicalModel<GlassDesertMobs.SunEater> {
    private final ModelPart root, torso, head, leftArm, rightArm, leftLeg, rightLeg, halo, core;
    public SunEaterModel(ModelPart root) {
        this.root=root; torso=root.getChild("torso"); head=root.getChild("head");
        leftArm=root.getChild("left_arm"); rightArm=root.getChild("right_arm");
        leftLeg=root.getChild("left_leg"); rightLeg=root.getChild("right_leg");
        halo=root.getChild("halo"); core=torso.getChild("core");
    }
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh=new MeshDefinition(); PartDefinition root=mesh.getRoot();
        PartDefinition torso=root.addOrReplaceChild("torso",CubeListBuilder.create()
            .texOffs(0,18).addBox(-5,-8,-3,10,16,6),PartPose.offset(0,-1,0));
        torso.addOrReplaceChild("core",CubeListBuilder.create().texOffs(32,18).addBox(-2,-2,-4,4,4,2),PartPose.ZERO);
        root.addOrReplaceChild("head",CubeListBuilder.create().texOffs(0,0).addBox(-4,-8,-4,8,8,8)
            .texOffs(32,0).addBox(-1,-3,-5,2,2,1),PartPose.offset(0,-11,0));
        root.addOrReplaceChild("left_arm",CubeListBuilder.create().texOffs(32,32).addBox(-2,-2,-2,4,14,4),
            PartPose.offset(7,-7,0));
        root.addOrReplaceChild("right_arm",CubeListBuilder.create().texOffs(32,32).addBox(-2,-2,-2,4,14,4),
            PartPose.offset(-7,-7,0));
        root.addOrReplaceChild("left_leg",CubeListBuilder.create().texOffs(48,32).addBox(-2,0,-2,4,10,4),
            PartPose.offset(3,7,0));
        root.addOrReplaceChild("right_leg",CubeListBuilder.create().texOffs(48,32).addBox(-2,0,-2,4,10,4),
            PartPose.offset(-3,7,0));
        root.addOrReplaceChild("halo",CubeListBuilder.create().texOffs(0,42).addBox(-6,-1,-1,12,2,2)
            .texOffs(0,46).addBox(-1,-6,-1,2,12,2),PartPose.offset(0,-19,0));
        return LayerDefinition.create(mesh,64,64);
    }
    @Override public ModelPart root(){return root;}
    @Override public void setupAnim(GlassDesertMobs.SunEater entity,float swing,float amount,float age,float yaw,float pitch) {
        head.yRot=yaw*Mth.DEG_TO_RAD; head.xRot=pitch*Mth.DEG_TO_RAD;
        leftArm.xRot=Mth.cos(swing*0.6662F+Mth.PI)*1.1F*amount-0.15F;
        rightArm.xRot=Mth.cos(swing*0.6662F)*1.1F*amount-0.15F;
        leftLeg.xRot=Mth.cos(swing*0.6662F)*0.9F*amount;
        rightLeg.xRot=Mth.cos(swing*0.6662F+Mth.PI)*0.9F*amount;
        halo.yRot=age*0.035F; halo.zRot=Mth.sin(age*0.05F)*0.08F;
        core.xRot=Mth.sin(age*0.12F)*0.12F;
    }
}