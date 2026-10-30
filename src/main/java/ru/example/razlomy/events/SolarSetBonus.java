package ru.example.razlomy.events;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import ru.example.razlomy.items.SolarEquipment;

/** Бонус полного комплекта солнечной брони. */
public final class SolarSetBonus {
    public static void initialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                if (isWearingFullSet(player)) {
                    // Короткое обновляемое сопротивление огню: бонус действует,
                    // пока надет весь комплект, и быстро исчезает после снятия.
                    if (player.tickCount % 40 == 0) {
                        player.addEffect(new MobEffectInstance(
                            MobEffects.FIRE_RESISTANCE, 80, 0, true, false, true
                        ));
                    }
                    if (player.tickCount % 20 == 0) {
                        player.serverLevel().sendParticles(
                            ParticleTypes.FLAME,
                            player.getX(), player.getY() + 1.0, player.getZ(),
                            3, 0.25, 0.35, 0.25, 0.005
                        );
                    }
                }
            }
        });
    }

    private static boolean isWearingFullSet(ServerPlayer player) {
        return is(player.getItemBySlot(EquipmentSlot.HEAD), SolarEquipment.HELMET)
            && is(player.getItemBySlot(EquipmentSlot.CHEST), SolarEquipment.CHESTPLATE)
            && is(player.getItemBySlot(EquipmentSlot.LEGS), SolarEquipment.LEGGINGS)
            && is(player.getItemBySlot(EquipmentSlot.FEET), SolarEquipment.BOOTS);
    }

    private static boolean is(ItemStack stack, net.minecraft.world.item.Item item) {
        return stack.is(item);
    }

    private SolarSetBonus() {}
}
