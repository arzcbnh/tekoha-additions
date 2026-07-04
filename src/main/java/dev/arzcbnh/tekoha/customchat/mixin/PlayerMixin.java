package dev.arzcbnh.tekoha.customchat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.arzcbnh.tekoha.customchat.CustomChatData;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public class PlayerMixin {
    @Unique private final Player self = (Player) (Object) this;

    @ModifyReturnValue(method = "getDisplayName", at = @At("RETURN"))
    private Component tekoha$getDisplayName(Component original) {
        if (!(self instanceof ServerPlayer serverPlayer)) {
            return original;
        }

        final var server = serverPlayer.level().getServer();
        final var data = CustomChatData.getInstance(server);
        final var color = data.getColor(self.getUUID());

        if (color == null) {
            return original;
        }

        return original.copy().withStyle(style -> style.withColor(color));
    }
}
