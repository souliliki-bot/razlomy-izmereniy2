package ru.example.razlomy.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.util.datafix.DataFixTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RiftReturnPoints extends SavedData {
    private static final String DATA_NAME = "razlomy_rift_return_points";

    public record ReturnPoint(
        ResourceKey<Level> dimension,
        double x,
        double y,
        double z,
        float yaw,
        float pitch
    ) {}

    private final Map<UUID, ReturnPoint> points = new HashMap<>();

    private static final SavedData.Factory<RiftReturnPoints> FACTORY =
        new SavedData.Factory<>(
            RiftReturnPoints::new,
            RiftReturnPoints::load,
            DataFixTypes.LEVEL
        );

    public static RiftReturnPoints getData(ServerPlayer player) {
        return player.getServer()
            .overworld()
            .getDataStorage()
            .computeIfAbsent(FACTORY, DATA_NAME);
    }

    public static void save(ServerPlayer player) {
        RiftReturnPoints data = getData(player);

        data.points.put(
            player.getUUID(),
            new ReturnPoint(
                player.serverLevel().dimension(),
                player.getX(),
                player.getY(),
                player.getZ(),
                player.getYRot(),
                player.getXRot()
            )
        );

        data.setDirty();
    }

    public static ReturnPoint get(ServerPlayer player) {
        return getData(player).points.get(player.getUUID());
    }

    public static void clear(ServerPlayer player) {
        RiftReturnPoints data = getData(player);
        data.points.remove(player.getUUID());
        data.setDirty();
    }

    private static RiftReturnPoints load(
        CompoundTag tag,
        HolderLookup.Provider registries
    ) {
        RiftReturnPoints data = new RiftReturnPoints();

        CompoundTag entries = tag.getCompound("points");

        for (String key : entries.getAllKeys()) {
            try {
                UUID uuid = UUID.fromString(key);
                CompoundTag point = entries.getCompound(key);

                ResourceKey<Level> dimension = ResourceKey.create(
                    Registries.DIMENSION,
                    ResourceLocation.parse(point.getString("dimension"))
                );

                data.points.put(
                    uuid,
                    new ReturnPoint(
                        dimension,
                        point.getDouble("x"),
                        point.getDouble("y"),
                        point.getDouble("z"),
                        point.getFloat("yaw"),
                        point.getFloat("pitch")
                    )
                );
            } catch (IllegalArgumentException ignored) {
            }
        }

        return data;
    }

    @Override
    public CompoundTag save(
        CompoundTag tag,
        HolderLookup.Provider registries
    ) {
        CompoundTag entries = new CompoundTag();

        for (Map.Entry<UUID, ReturnPoint> entry : points.entrySet()) {
            ReturnPoint point = entry.getValue();
            CompoundTag savedPoint = new CompoundTag();

            savedPoint.putString(
                "dimension",
                point.dimension().location().toString()
            );
            savedPoint.putDouble("x", point.x());
            savedPoint.putDouble("y", point.y());
            savedPoint.putDouble("z", point.z());
            savedPoint.putFloat("yaw", point.yaw());
            savedPoint.putFloat("pitch", point.pitch());

            entries.put(entry.getKey().toString(), savedPoint);
        }

        tag.put("points", entries);
        return tag;
    }
}
