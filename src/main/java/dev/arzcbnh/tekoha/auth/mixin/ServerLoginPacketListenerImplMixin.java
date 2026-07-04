package dev.arzcbnh.tekoha.auth.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.arzcbnh.tekoha.auth.AuthData;
import dev.arzcbnh.tekoha.auth.HostNameConnectionAccess;
import java.net.SocketAddress;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;
import net.minecraft.server.players.NameAndId;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerLoginPacketListenerImpl.class)
public abstract class ServerLoginPacketListenerImplMixin {
    @Shadow
    @Final
    private MinecraftServer server;

    @Shadow
    @Final
    private Connection connection;

    @WrapOperation(
            method = "verifyLoginAndFinishConnectionSetup",
            at =
                    @At(
                            value = "INVOKE",
                            target =
                                    "Lnet/minecraft/server/players/PlayerList;canPlayerLogin(Ljava/net/SocketAddress;Lnet/minecraft/server/players/NameAndId;)Lnet/minecraft/network/chat/Component;"))
    private Component tekoha$canPlayerLogin(
            PlayerList instance, SocketAddress address, NameAndId nameAndId, Operation<Component> original) {
        final var error = original.call(instance, address, nameAndId);

        if (error != null || this.server.usesAuthentication()) {
            return error;
        }

        final var data = AuthData.getInstance(this.server);
        final var hostName = ((HostNameConnectionAccess) this.connection).tekoha$getHostName();
        boolean isValid;

        try {
            final var token = hostName.substring(0, hostName.indexOf('.'));
            isValid = data.isValid(nameAndId.id(), token);
        } catch (StringIndexOutOfBoundsException e) {
            isValid = false;
        }

        return isValid ? null : Component.translatable("tekoha.auth.login.fail");
    }
}
