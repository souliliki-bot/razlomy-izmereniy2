package ru.example.razlomy;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Set;
import net.minecraft.world.entity.RelativeMovement;

public final class RazlomyIzmereniy implements ModInitializer {
    private static final String MOD_ID = "razlomy_izmereniy";

    private static ResourceKey<Level> dimension(String id) {
        return ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(MOD_ID, id));
    }

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(Commands.literal("razlom")
                .then(Commands.literal("power").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 30, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP, 20 * 30, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 30, 0));
                    player.serverLevel().sendParticles(ParticleTypes.PORTAL,
                            player.getX(), player.getY() + 1.0, player.getZ(),
                            100, 0.7, 1.0, 0.7, 0.15);
                    player.sendSystemMessage(Component.literal(
                            "§5✦ Разлом пробудил твою силу! §dУсиления действуют 30 секунд."));
                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("portal").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    Vec3 look = player.getLookAngle();
                    double x = player.getX() + look.x * 3.0;
                    double y = player.getY() + 1.0 + look.y * 3.0;
                    double z = player.getZ() + look.z * 3.0;
                    player.serverLevel().sendParticles(ParticleTypes.PORTAL, x, y, z,
                            180, 0.8, 1.2, 0.8, 0.25);
                    player.serverLevel().sendParticles(ParticleTypes.REVERSE_PORTAL, x, y, z,
                            60, 0.5, 0.8, 0.5, 0.1);
                    player.sendSystemMessage(Component.literal(
                            "§5🌀 Разлом мерцает! Выбери измерение: /razlom enter echo, forge, garden, desert, ocean, storm, frost, hive, mire, necropolis, wild или chrono."));
                    return Command.SINGLE_SUCCESS;
                }))
                .then(Commands.literal("enter")
                    .then(Commands.literal("echo").executes(c -> travel(c.getSource().getPlayerOrException(), "echo_limit", "Эхо-Предел")))
                    .then(Commands.literal("forge").executes(c -> travel(c.getSource().getPlayerOrException(), "rift_forge", "Кузня Разлома")))
                    .then(Commands.literal("garden").executes(c -> travel(c.getSource().getPlayerOrException(), "zero_sun_garden", "Сад Нулевого Солнца")))
                    .then(Commands.literal("desert").executes(c -> travel(c.getSource().getPlayerOrException(), "glass_desert", "Стеклянная Пустыня")))
                    .then(Commands.literal("ocean").executes(c -> travel(c.getSource().getPlayerOrException(), "silent_ocean", "Безмолвный Океан")))
                    .then(Commands.literal("storm").executes(c -> travel(c.getSource().getPlayerOrException(), "storm_limit", "Грозовой Предел")))
                    .then(Commands.literal("frost").executes(c -> travel(c.getSource().getPlayerOrException(), "ice_crown", "Ледяная Корона")))
                    .then(Commands.literal("hive").executes(c -> travel(c.getSource().getPlayerOrException(), "mechanical_hive", "Механический Улей")))
                    .then(Commands.literal("mire").executes(c -> travel(c.getSource().getPlayerOrException(), "crimson_mire", "Багровая Трясина")))
                    .then(Commands.literal("necropolis").executes(c -> travel(c.getSource().getPlayerOrException(), "lunar_necropolis", "Лунный Некрополь")))
                    .then(Commands.literal("wild").executes(c -> travel(c.getSource().getPlayerOrException(), "wild_rift", "Дикий Излом")))
                    .then(Commands.literal("chrono").executes(c -> travel(c.getSource().getPlayerOrException(), "chrono_archive", "Хроно-Архив"))))
                .then(Commands.literal("return").executes(context ->
                    travel(context.getSource().getPlayerOrException(), "minecraft:overworld", "Обычный мир")))
                .then(Commands.literal("help").executes(context -> {
                    context.getSource().sendSuccess(() -> Component.literal(
                        "§5Разломы измерений§7: /razlom power, /razlom portal, /razlom enter <echo|forge|garden|desert|ocean|storm|frost|hive|mire|necropolis|wild|chrono>, /razlom return"), false);
                    return Command.SINGLE_SUCCESS;
                }))
            )
        );
    }

    private static int travel(ServerPlayer player, String id, String label) {
        ResourceKey<Level> key = id.contains(":")
                ? ResourceKey.create(net.minecraft.core.registries.Registries.DIMENSION, ResourceLocation.parse(id))
                : dimension(id);
        ServerLevel destination = player.getServer().getLevel(key);
        if (destination == null) {
            player.sendSystemMessage(Component.literal(
                    "§cИзмерение «" + label + "» не загружено. Проверь JSON-ресурс измерения и перезапусти мир."));
            return 0;
        }

        double x = 0.5;
        double z = 0.5;
        double y = Math.max(destination.getMinBuildHeight() + 2.0, Math.min(destination.getSeaLevel() + 8.0, (destination.getMinBuildHeight() + destination.getMaxBuildHeight() - destination.getMinBuildHeight() - 1) - 2.0));
        player.serverLevel().sendParticles(ParticleTypes.PORTAL,
                player.getX(), player.getY() + 1, player.getZ(), 80, 0.5, 1, 0.5, 0.15);
        player.teleportTo(destination, x, y, z, Set.<RelativeMovement>of(), player.getYRot(), player.getXRot());
        player.sendSystemMessage(Component.literal("§5🌀 Ты вошёл в измерение: §d" + label + "§5!"));
        return Command.SINGLE_SUCCESS;
    }
}
