package dev.arzcbnh.tekoha.mixin;

import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.CommonColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow
    public ServerPlayer player;

//    @Inject(method = "sendPlayerChatMessage", at = @At("HEAD"), cancellable = true)
//    private void tekoha$sendPlayerChatMessage(PlayerChatMessage playerChatMessage, ChatType.Bound bound, CallbackInfo ci) {
//        final var username = ((ServerCommonPacketListenerImplAccessor) this).tekoha$getServer().getPlayerList().getPlayer(playerChatMessage.sender()).getDisplayName();
//        final var message = playerChatMessage.decoratedContent();
//        final var body = Component.empty().append(username).append(Component.literal(":\n")).append(message);
//        player.sendSystemMessage(body);
//        ci.cancel();
//        final var packet = new ClientboundSystemChatPacket()
//    }
}
