package dev.arzcbnh.tekoha.xp.mixin;

import dev.arzcbnh.tekoha.xp.PlayerMixinAccess;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Player.class)
public abstract class PlayerMixin implements PlayerMixinAccess {
    @Shadow
    public int experienceLevel;

    @Shadow
    public float experienceProgress;

    @Shadow
    public abstract int getXpNeededForNextLevel();

    @Override
    public int tekoha$getTotalXp() {
        final int level = this.experienceLevel;
        final long experienceAtLevel;

        // https://minecraft.wiki/w/Experience#Leveling_up
        if (level <= 16) {
            experienceAtLevel = (long) level * level + 6L * level;
        } else if (level <= 31) {
            experienceAtLevel = (5L * level * level - 81L * level + 720L) / 2L;
        } else {
            experienceAtLevel = (9L * level * level - 325L * level + 4440L) / 2L;
        }

        final int progressInCurrentLevel = (int) (this.experienceProgress * this.getXpNeededForNextLevel());

        return Math.toIntExact(experienceAtLevel + progressInCurrentLevel);
    }
}
