package dev.arzcbnh.tekoha.auth;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.arzcbnh.tekoha.TekohaAdditions;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jspecify.annotations.Nullable;

public class AuthData extends SavedData {
    public static final int VERSION = 1;

    public static final Codec<AuthData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                    Codec.INT.fieldOf("Version").forGetter(inst -> inst.version),
                    Codec.unboundedMap(UUIDUtil.STRING_CODEC, Codec.STRING)
                            .fieldOf("Tokens")
                            .forGetter(inst -> inst.tokens))
            .apply(builder, AuthData::new));

    public static final SavedDataType<AuthData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(TekohaAdditions.NAMESPACE, "auth"),
            () -> new AuthData(VERSION, Map.of()),
            CODEC,
            null);

    private final int version;
    private final Map<UUID, String> tokens;

    private AuthData(int version, Map<UUID, String> tokens) {
        this.version = version;
        this.tokens = new HashMap<>(tokens);
    }

    public static AuthData getInstance(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public void setToken(UUID uuid, @Nullable String token) {
        if (token == null) {
            tokens.remove(uuid);
        } else {
            tokens.put(uuid, token);
        }
        this.setDirty();
    }

    public boolean isValid(UUID uuid, String token) {
        final var stored = tokens.get(uuid);
        return stored != null && stored.equals(token);
    }
}
