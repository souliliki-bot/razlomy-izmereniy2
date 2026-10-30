package ru.example.razlomy.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.example.razlomy.items.FrostArmor;

/** Бонус полного комплекта Ледяной Короны. */
public final class FrostSetBonus {
    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (isWearingFullSet(player)) {
                    if (player.tickCount % 40 == 0) {
                        player.addEffect(new MobEffectInstance(
                            MobEffects.RESISTANCE, 80, 0, true, false, true
                        ));
                    }
                    if (player.tickCount % 20 == 0) {
                        player.serverLevel().sendParticles(
                            ParticleTypes.SNOWFLAKE,
                            player.getX(), player.getY() + 1.0, player.getZ(),
                            4, 0.3, 0.4, 0.3, 0.005
                        );
                    }
                }
            }
        });
    }

    private static boolean isWearingFullSet(ServerPlayer player) {
        return is(player.getItemBySlot(EquipmentSlot.HEAD), FrostArmor.HELMET)
            && is(player.getItemBySlot(EquipmentSlot.CHEST), FrostArmor.CHESTPLATE)
            && is(player.getItemBySlot(EquipmentSlot.LEGS), FrostArmor.LEGGINGS)
            && is(player.getItemBySlot(EquipmentSlot.FEET), FrostArmor.BOOTS);
    }

    private static boolean is(ItemStack stack, Item item) { return stack.is(item); }
    private FrostSetBonus() {}
}
