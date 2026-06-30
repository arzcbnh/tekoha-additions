package dev.arzcbnh.tekoha.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    private final Player self = (Player) (Object) this;

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Component tekoha$getDisplayName(Component original) {
        final var scoreboard = self.level().getScoreboard();
        final var objective = scoreboard.getObjective("tekoha_display_color");

        if (objective == null) {
            return original;
        }

        final var color = scoreboard.getPlayerScoreInfo((Entity) (Object) this, objective);

        if (color == null) {
            return original;
        }

        return original.copy().withStyle(style -> style.withColor(color.value()));

    }
}
