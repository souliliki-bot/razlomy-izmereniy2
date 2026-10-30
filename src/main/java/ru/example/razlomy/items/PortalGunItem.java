package ru.example.razlomy.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * Компактная портальная пушка: выстреливает лучом и переносит владельца
 * к поверхности, в которую попал луч. Не пересекает границы измерений.
 */
public final class PortalGunItem extends Item {
    public PortalGunItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(
        Level level, Player player, InteractionHand hand
    ) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        Vec3 start = player.getEyePosition();
        Vec3 end = start.add(player.getLookAngle().scale(48.0D));
        BlockHitResult hit = level.clip(new ClipContext(
            start, end, ClipContext.Block.COLLIDER,
            ClipContext.Fluid.NONE, player
        ));

        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.fail(stack);
        }

        BlockPos destination = hit.getBlockPos().relative(hit.getDirection());
        BlockPos feet = destination.above();
        if (!level.getBlockState(feet).getCollisionShape(level, feet).isEmpty()
            || !level.getBlockState(feet.above()).getCollisionShape(level, feet.above()).isEmpty()) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            Vec3 landing = Vec3.atBottomCenterOf(feet);
            level.addParticle(ParticleTypes.PORTAL,
                player.getX(), player.getY() + 1.0D, player.getZ(),
                0.0D, 0.2D, 0.0D);
            if (player instanceof ServerPlayer serverPlayer) {
                serverPlayer.teleportTo(landing.x, landing.y, landing.z);
            } else {
                player.teleportTo(landing.x, landing.y, landing.z);
            }
            level.addParticle(ParticleTypes.REVERSE_PORTAL,
                landing.x, landing.y + 0.5D, landing.z,
                0.0D, 0.2D, 0.0D);
            player.getCooldowns().addCooldown(this, 40);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }
}
