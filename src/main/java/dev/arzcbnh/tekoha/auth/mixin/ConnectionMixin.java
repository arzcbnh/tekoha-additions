package dev.arzcbnh.tekoha.auth.mixin;

import dev.arzcbnh.tekoha.auth.HostNameConnectionAccess;
import net.minecraft.network.Connection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Connection.class)
public class ConnectionMixin implements HostNameConnectionAccess {
    @Unique private String tekoha$hostName;

    public String tekoha$getHostName() {
        return this.tekoha$hostName;
    }

    public void tekoha$setHostName(String hostName) {
        this.tekoha$hostName = hostName;
    }
}
