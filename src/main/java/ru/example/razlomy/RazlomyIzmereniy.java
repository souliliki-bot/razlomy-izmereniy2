package ru.example.razlomy;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.RelativeMovement;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import ru.example.razlomy.data.RiftReturnPoints;

import java.util.Set;

public final class RazlomyIzmereniy implements ModInitializer {

    private static final String MOD_ID = "razlomy_izmereniy";

    private static ResourceKey<Level> dimension(String id) {
        return ResourceKey.create(
            net.minecraft.core.registries.Registries.DIMENSION,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, id)
        );
    }

    @Override
    public void onInitialize() {
        ru.example.razlomy.blocks.RiftBlocks.initialize();
        ru.example.razlomy.items.RiftItems.initialize();
        ru.example.razlomy.items.RiftCrystalAbility.initialize();
        ru.example.razlomy.items.RiftSwordAbility.initialize();
        ru.example.razlomy.events.RiftCombat.initialize();

        CommandRegistrationCallback.EVENT.register(
            (dispatcher, registryAccess, environment) ->
                dispatcher.register(
                    Commands.literal("razlom")

                        .then(Commands.literal("power").executes(context -> {
                            ServerPlayer player =
                                context.getSource().getPlayerOrException();

                            player.addEffect(new MobEffectInstance(
                                MobEffects.MOVEMENT_SPEED, 600, 1
                            ));
                            player.addEffect(new MobEffectInstance(
                                MobEffects.JUMP, 600, 1
                            ));
                            player.addEffect(new MobEffectInstance(
                                MobEffects.NIGHT_VISION, 600, 0
                            ));

                            player.serverLevel().sendParticles(
                                ParticleTypes.PORTAL,
                                player.getX(), player.getY() + 1,
                                player.getZ(), 100,
                                0.7, 1.0, 0.7, 0.15
                            );

                            player.sendSystemMessage(Component.literal(
                                "§5✦ Разлом пробудил твою силу! §dУсиления действуют 30 секунд."
                            ));

                            return Command.SINGLE_SUCCESS;
                        }))

                        .then(Commands.literal("portal").executes(context -> {
                            ServerPlayer player =
                                context.getSource().getPlayerOrException();

                            Vec3 look = player.getLookAngle();
                            double x = player.getX() + look.x * 3.0;
                            double y = player.getY() + 1.0 + look.y * 3.0;
                            double z = player.getZ() + look.z * 3.0;

                            player.serverLevel().sendParticles(
                                ParticleTypes.PORTAL,
                                x, y, z, 180,
                                0.8, 1.2, 0.8, 0.25
                            );

                            player.serverLevel().sendParticles(
                                ParticleTypes.REVERSE_PORTAL,
                                x, y, z, 60,
                                0.5, 0.8, 0.5, 0.1
                            );

                            openPortalMenu(player);
                            return Command.SINGLE_SUCCESS;
                        }))

                        .then(Commands.literal("enter")
                            .then(Commands.literal("echo")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "echo_limit", "Предел Эха"
                                )))
                            .then(Commands.literal("forge")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "rift_forge", "Кузница Разлома"
                                )))
                            .then(Commands.literal("garden")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "zero_sun_garden", "Сад Нулевого Солнца"
                                )))
                            .then(Commands.literal("desert")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "glass_desert", "Стеклянная Пустыня"
                                )))
                            .then(Commands.literal("ocean")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "silent_ocean", "Безмолвный Океан"
                                )))
                            .then(Commands.literal("storm")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "storm_limit", "Грозовой Предел"
                                )))
                            .then(Commands.literal("frost")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "ice_crown", "Ледяная Корона"
                                )))
                            .then(Commands.literal("hive")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "mechanical_hive", "Механический Улей"
                                )))
                            .then(Commands.literal("mire")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "crimson_mire", "Багровая Трясина"
                                )))
                            .then(Commands.literal("necropolis")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "lunar_necropolis", "Лунная Некрополь"
                                )))
                            .then(Commands.literal("wild")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "wild_rift", "Дикий Разлом"
                                )))
                            .then(Commands.literal("chrono")
                                .executes(c -> travel(
                                    c.getSource().getPlayerOrException(),
                                    "chrono_archive", "Хроно-Архив"
                                )))
                        )

                        .then(Commands.literal("return").executes(context ->
                            returnToOrigin(
                                context.getSource().getPlayerOrException()
                            )
                        ))

                        .then(Commands.literal("help").executes(context -> {
                            context.getSource().sendSuccess(
                                () -> Component.literal(
                                    "§5Разломы измерений§7: /razlom portal, "
                                    + "/razlom power, /razlom return"
                                ),
                                false
                            );
                            return Command.SINGLE_SUCCESS;
                        }))
                )
        );
    }

    private static void openPortalMenu(ServerPlayer player) {
        player.sendSystemMessage(Component.literal(""));
        player.sendSystemMessage(Component.literal(
            "§5§l✦ МЕНЮ РАЗЛОМОВ ✦"
        ));
        player.sendSystemMessage(Component.literal(
            "§7Выбери измерение нажатием на его название:"
        ));

        portalOption(player, "§d◆ Предел Эха", "echo");
        portalOption(player, "§6◆ Кузница Разлома", "forge");
        portalOption(player, "§e◆ Сад Нулевого Солнца", "garden");
        portalOption(player, "§f◆ Стеклянная Пустыня", "desert");
        portalOption(player, "§b◆ Безмолвный Океан", "ocean");
        portalOption(player, "§9◆ Грозовой Предел", "storm");
        portalOption(player, "§3◆ Ледяная Корона", "frost");
        portalOption(player, "§a◆ Механический Улей", "hive");
        portalOption(player, "§c◆ Багровая Трясина", "mire");
        portalOption(player, "§5◆ Лунный Некрополь", "necropolis");
        portalOption(player, "§2◆ Дикий Разлом", "wild");
        portalOption(player, "§6◆ Хроно-Архив", "chrono");

        player.sendSystemMessage(Component.literal(""));
        player.sendSystemMessage(Component.literal(
            "§7Для возвращения используй §d/razlom return"
        ));
    }

    private static void portalOption(
        ServerPlayer player,
        String name,
        String id
    ) {
        Component option = Component.literal(name)
            .withStyle(style -> style
                .withClickEvent(new ClickEvent(
                    ClickEvent.Action.RUN_COMMAND,
                    "/razlom enter " + id
                ))
                .withUnderlined(true)
            );

        player.sendSystemMessage(option);
    }

    private static int travel(
        ServerPlayer player,
        String id,
        String label
    ) {
        ResourceKey<Level> key = dimension(id);
        ServerLevel destination = player.getServer().getLevel(key);

        if (destination == null) {
            player.sendSystemMessage(Component.literal(
                "§cИзмерение «" + label
                + "» не загружено. Проверь JSON измерения."
            ));
            return 0;
        }

        if (player.serverLevel().dimension().equals(key)) {
            player.sendSystemMessage(Component.literal(
                "§eТы уже находишься в этом измерении."
            ));
            return 0;
        }

        // Сохраняем только первую точку входа.
        // Последующие переходы между измерениями её не перезаписывают.
        if (RiftReturnPoints.get(player) == null) {
            RiftReturnPoints.save(player);
        }

        double x = 0.5;
        double z = 0.5;
        double y = Math.max(
            destination.getMinBuildHeight() + 2.0,
            Math.min(
                destination.getSeaLevel() + 8.0,
                destination.getMaxBuildHeight() - 3.0
            )
        );

        player.serverLevel().sendParticles(
            ParticleTypes.PORTAL,
            player.getX(), player.getY() + 1,
            player.getZ(), 100,
            0.5, 1.0, 0.5, 0.15
        );

        player.teleportTo(
            destination,
            x, y, z,
            Set.<RelativeMovement>of(),
            player.getYRot(),
            player.getXRot()
        );

        destination.sendParticles(
            ParticleTypes.REVERSE_PORTAL,
            x, y + 1, z, 100,
            0.8, 1.0, 0.8, 0.15
        );

        player.sendSystemMessage(Component.literal(
            "§5🌀 Ты вошёл в измерение: §d" + label + "§5!"
        ));

        return Command.SINGLE_SUCCESS;
    }

    private static int returnToOrigin(ServerPlayer player) {
        RiftReturnPoints.ReturnPoint point =
            RiftReturnPoints.get(player);

        if (point == null) {
            player.sendSystemMessage(Component.literal(
                "§cУ тебя нет сохранённой точки возврата."
            ));
            return 0;
        }

        ServerLevel destination =
            player.getServer().getLevel(point.dimension());

        if (destination == null) {
            player.sendSystemMessage(Component.literal(
                "§cИсходное измерение недоступно!"
            ));
            return 0;
        }

        player.serverLevel().sendParticles(
            ParticleTypes.PORTAL,
            player.getX(), player.getY() + 1,
            player.getZ(), 100,
            0.5, 1.0, 0.5, 0.15
        );

        player.teleportTo(
            destination,
            point.x(),
            point.y(),
            point.z(),
            Set.<RelativeMovement>of(),
            point.yaw(),
            point.pitch()
        );

        RiftReturnPoints.clear(player);

        destination.sendParticles(
            ParticleTypes.REVERSE_PORTAL,
            point.x(), point.y() + 1, point.z(),
            100, 0.8, 1.0, 0.8, 0.15
        );

        player.sendSystemMessage(Component.literal(
            "§d✦ Ты вернулся в исходную точку!"
        ));

        return Command.SINGLE_SUCCESS;
    }
}
