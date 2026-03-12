package dev.arzcbnh.tekoha.effect;

import eu.pb4.polymer.core.api.other.PolymerStatusEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.portal.TeleportTransition;

public class RecallMobEffect extends InstantenousMobEffect implements PolymerStatusEffect {
    public RecallMobEffect() {
        super(MobEffectCategory.NEUTRAL, 0x0AB1D1);
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int i) {
        if (!(livingEntity instanceof ServerPlayer player)) {
            return false;
        }

        final TeleportTransition respawnTarget = player.findRespawnPositionAndUseSpawnBlock(player.isAlive(), TeleportTransition.DO_NOTHING);
        player.teleport(respawnTarget);
        return true;
    }
}
