package ru.example.razlomy.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.Level;

/** Пять существ Стеклянной пустыни. На первом этапе используют базовую модель кадавра. */
public final class GlassDesertMobs {
    public static class SandHunter extends Husk {
        public SandHunter(EntityType<? extends Husk> type, Level level) { super(type, level); }
    }
    public static class SolarScorpion extends Husk {
        public SolarScorpion(EntityType<? extends Husk> type, Level level) { super(type, level); }
    }
    public static class GlassWanderer extends Husk {
        public GlassWanderer(EntityType<? extends Husk> type, Level level) { super(type, level); }
    }
    public static class GoldenSandGolem extends Husk {
        public GoldenSandGolem(EntityType<? extends Husk> type, Level level) { super(type, level); }
    }
    public static class SunEater extends Husk {
        public SunEater(EntityType<? extends Husk> type, Level level) { super(type, level); }
    }
    private GlassDesertMobs() {}
}
