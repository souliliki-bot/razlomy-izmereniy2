package ru.example.razlomy.items;


import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import ru.example.razlomy.data.RiftReturnPoints;

import java.util.Set;

public final class RiftCrystalAbility {

    private static final ResourceKey<Level> RIFT_FORGE =
        ResourceKey.create(
            net.minecraft.core.registries.Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(
                "razlomy_izmereniy", "rift_forge"
            )
        );

    public static void initialize() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            ItemStack stack = player.getItemInHand(hand);

            if (stack.getItem() != RiftItems.RIFT_CRYSTAL) {
                return InteractionResultHolder.pass(stack);
            }

            if (!(player instanceof ServerPlayer serverPlayer)) {
                return InteractionResultHolder.pass(stack);
            }

            if (serverPlayer.getCooldowns().isOnCooldown(
                RiftItems.RIFT_CRYSTAL
            )) {
                return InteractionResultHolder.fail(stack);
            }

            boolean returning =
                serverPlayer.serverLevel().dimension().equals(RIFT_FORGE);

            ServerLevel destination;
            double x;
            double y;
            double z;

            float yaw = serverPlayer.getYRot();
            float pitch = serverPlayer.getXRot();

            if (returning) {
                RiftReturnPoints.ReturnPoint point =
                    RiftReturnPoints.get(serverPlayer);

                if (point == null) {
                    destination = serverPlayer.getServer()
                        .getLevel(Level.OVERWORLD);

                    if (destination == null) {
                        return InteractionResultHolder.fail(stack);
                    }

                    BlockPos spawn = destination.getSharedSpawnPos();

                    x = spawn.getX() + 0.5;
                    y = spawn.getY() + 1.0;
                    z = spawn.getZ() + 0.5;
                } else {
                    destination = serverPlayer.getServer()
                        .getLevel(point.dimension());

                    if (destination == null) {
                        serverPlayer.sendSystemMessage(
                            Component.literal(
                                "§cИсходное измерение недоступно!"
                            )
                        );
                        return InteractionResultHolder.fail(stack);
                    }

                    x = point.x();
                    y = point.y();
                    z = point.z();
                    yaw = point.yaw();
                    pitch = point.pitch();
                }
            } else {
                destination = serverPlayer.getServer()
                    .getLevel(RIFT_FORGE);

                if (destination == null) {
                    serverPlayer.sendSystemMessage(
                        Component.literal(
                            "§cКузница Разломов не загружена!"
                        )
                    );
                    return InteractionResultHolder.fail(stack);
                }

                // Не перезаписываем уже сохранённую точку.
                if (RiftReturnPoints.get(serverPlayer) == null) {
                    RiftReturnPoints.save(serverPlayer);
                }

                BlockPos spawn = destination.getSharedSpawnPos();

                x = spawn.getX() + 0.5;
                y = spawn.getY() + 1.0;
                z = spawn.getZ() + 0.5;
            }

            serverPlayer.serverLevel().sendParticles(
                ParticleTypes.PORTAL,
                serverPlayer.getX(),
                serverPlayer.getY() + 1.0,
                serverPlayer.getZ(),
                100, 0.7, 1.0, 0.7, 0.15
            );

            serverPlayer.teleportTo(
                destination,
                x, y, z,
                Set.of(),
                yaw,
                pitch
            );

            // Удаляем точку только после возвращения.
            if (returning) {
                RiftReturnPoints.clear(serverPlayer);
            }

            destination.sendParticles(
                ParticleTypes.REVERSE_PORTAL,
                x, y + 1.0, z,
                100, 0.7, 1.0, 0.7, 0.15
            );

            serverPlayer.getCooldowns().addCooldown(
                RiftItems.RIFT_CRYSTAL, 100
            );

            serverPlayer.sendSystemMessage(
                Component.literal(
                    returning
                        ? "§d✦ Ты вернулся в исходную точку!"
                        : "§5🌀 Добро пожаловать в Кузницу Разломов!"
                )
            );

            return InteractionResultHolder.success(stack);
        });
    }

    private RiftCrystalAbility() {}
}
