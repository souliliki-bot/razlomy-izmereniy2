package ru.example.razlomy.items;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class RiftSwordAbility {
    public static void initialize() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getItemInHand(hand);

            if (stack.getItem() != RiftItems.RIFT_SWORD) {
                return InteractionResultHolder.pass(stack);
            }

            if (world instanceof ServerLevel serverWorld) {
                Vec3 look = player.getLookAngle();
                Vec3 center = player.position().add(look.scale(3.0));

                serverWorld.sendParticles(
                    ParticleTypes.PORTAL,
                    center.x, center.y + 1.0, center.z,
                    70, 1.0, 0.8, 1.0, 0.2
                );

                serverWorld.sendParticles(
                    ParticleTypes.END_ROD,
                    center.x, center.y + 1.0, center.z,
                    20, 0.8, 0.8, 0.8, 0.1
                );

                AABB area = player.getBoundingBox()
                    .inflate(4.0)
                    .move(look.scale(2.0));

                for (var target : serverWorld.getEntitiesOfClass(
                    net.minecraft.world.entity.LivingEntity.class,
                    area,
                    entity -> entity != player && entity.isAlive()
                )) {
                    Vec3 push = target.position()
                        .subtract(player.position())
                        .normalize();

                    target.push(push.x * 1.8, 0.7, push.z * 1.8);
                    target.hurt(
                        player.damageSources().playerAttack(player),
                        4.0F
                    );
                }
            }

            return InteractionResultHolder.success(stack);
        });
    }

    private RiftSwordAbility() {}
}
