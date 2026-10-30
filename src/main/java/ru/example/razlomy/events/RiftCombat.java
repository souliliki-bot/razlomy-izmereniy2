package ru.example.razlomy.events;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerPlayer;

public final class RiftCombat {
    public static void initialize() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            ItemStack stack = player.getItemInHand(hand);

            if (!stack.isEmpty()
                && stack.is(net.minecraft.core.registries.BuiltInRegistries.ITEM.get(
                    net.minecraft.resources.ResourceLocation.fromNamespaceAndPath(
                        "razlomy_izmereniy", "rift_sword"
                    )
                ))) {
                if (world instanceof net.minecraft.server.level.ServerLevel serverWorld) {
                    serverWorld.sendParticles(
                        ParticleTypes.PORTAL,
                        entity.getX(),
                        entity.getY() + entity.getBbHeight() / 2.0,
                        entity.getZ(),
                        25,
                        0.4, 0.5, 0.4,
                        0.15
                    );
                    serverWorld.sendParticles(
                        ParticleTypes.END_ROD,
                        entity.getX(),
                        entity.getY() + entity.getBbHeight() / 2.0,
                        entity.getZ(),
                        8,
                        0.3, 0.4, 0.3,
                        0.05
                    );
                }
            }

            return InteractionResult.PASS;
        });
    }

    private RiftCombat() {}
}
