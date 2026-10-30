package ru.example.razlomy.entity.generated;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Husk;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

/** Морозный охотник Ледяной Короны: замедляет добычу ледяным ударом. */
public final class FrostStalker extends Husk {
    public FrostStalker(EntityType<? extends Husk> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        boolean hit = super.doHurtTarget(target);
        if (hit && target instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 80, 0), this);
        }
        return hit;
    }
}
