package dev.arzcbnh.tekoha.customchat;

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

public class CustomChatData extends SavedData {
    public static final int VERSION = 1;

    public static final Codec<CustomChatData> CODEC = RecordCodecBuilder.create(builder -> builder.group(
                    Codec.INT.fieldOf("Version").forGetter(inst -> inst.version),
                    Codec.unboundedMap(UUIDUtil.STRING_CODEC, Codec.INT)
                            .fieldOf("Colors")
                            .forGetter(inst -> inst.colors))
            .apply(builder, CustomChatData::new));

    public static final SavedDataType<CustomChatData> TYPE = new SavedDataType<>(
            Identifier.fromNamespaceAndPath(TekohaAdditions.NAMESPACE, "custom-chat"),
            () -> new CustomChatData(VERSION, new HashMap<>()),
            CODEC,
            null);

    private final int version;
    private final Map<UUID, Integer> colors;

    public CustomChatData(int version, Map<UUID, Integer> colors) {
        this.version = version;
        this.colors = new HashMap<>(colors);
    }

    public static CustomChatData getInstance(MinecraftServer server) {
        return server.getDataStorage().computeIfAbsent(TYPE);
    }

    public @Nullable Integer getColor(UUID uuid) {
        return colors.get(uuid);
    }

    /**
     * Sets the color of the player with the given UUID.
     *
     * @param uuid the UUID of the player.
     * @param color the 24-bit RGB code of the desired color, or {@code null} to remove the color.
     * @throws IllegalArgumentException if the color code is not between 0 and 0xFFFFFF, inclusive.
     */
    public void setColor(UUID uuid, @Nullable Integer color) {
        if (color == null) {
            colors.remove(uuid);
        } else if (color >= 0 && color <= 0xFFFFFF) {
            colors.put(uuid, color);
        } else {
            throw new IllegalArgumentException("Color code must be between 0 and 0xFFFFFF, inclusive.");
        }

        this.setDirty();
    }
}
