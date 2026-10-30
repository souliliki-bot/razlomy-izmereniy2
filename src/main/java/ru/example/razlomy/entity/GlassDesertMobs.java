package ru.example.razlomy.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.level.Level;

/** Существа Стеклянной пустыни с разными боевыми приёмами и наборами целей. */
public final class GlassDesertMobs {
    public static class SandHunter extends Husk {
        public SandHunter(EntityType<? extends Husk> type, Level level) { super(type, level); }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.35D, true));
        }
    }

    public static class SolarScorpion extends Husk {
        public SolarScorpion(EntityType<? extends Husk> type, Level level) { super(type, level); }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.5D, true));
        }

        @Override
        public boolean doHurtTarget(ServerLevel level, Entity target) {
            boolean hit = super.doHurtTarget(level, target);
            if (hit && target instanceof net.minecraft.world.entity.LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0), this);
            }
            return hit;
        }
    }

    public static class GlassWanderer extends Husk {
        public GlassWanderer(EntityType<? extends Husk> type, Level level) { super(type, level); }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(7, new RandomStrollGoal(this, 0.75D));
        }
    }

    public static class GoldenSandGolem extends Husk {
        public GoldenSandGolem(EntityType<? extends Husk> type, Level level) { super(type, level); }

        @Override
        public boolean doHurtTarget(ServerLevel level, Entity target) {
            boolean hit = super.doHurtTarget(level, target);
            if (hit) {
                target.push(0.0D, 0.65D, 0.0D);
                target.hurtMarked = true;
            }
            return hit;
        }
    }

    public static class SunEater extends Husk {
        public SunEater(EntityType<? extends Husk> type, Level level) { super(type, level); }

        @Override
        protected void registerGoals() {
            super.registerGoals();
            this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        }

        @Override
        public boolean doHurtTarget(ServerLevel level, Entity target) {
            boolean hit = super.doHurtTarget(level, target);
            if (hit) {
                target.igniteForSeconds(4.0F);
                if (target instanceof net.minecraft.world.entity.LivingEntity living) {
                    living.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 0), this);
                }
            }
            return hit;
        }
    }

    private GlassDesertMobs() {}
}
