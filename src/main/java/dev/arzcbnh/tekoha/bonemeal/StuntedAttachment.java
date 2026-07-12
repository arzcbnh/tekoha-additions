package dev.arzcbnh.tekoha.bonemeal;

import com.mojang.serialization.Codec;
import dev.arzcbnh.tekoha.TekohaAdditions;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

public class StuntedAttachment {
    private static final AttachmentType<Set<BlockPos>> TYPE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(TekohaAdditions.NAMESPACE, "stunted"),
            builder -> builder.persistent(Codec.list(BlockPos.CODEC).xmap(HashSet::new, List::copyOf))
                    .initializer(HashSet::new));

    private final ChunkAccess chunk;
    private final BlockPos pos;

    public StuntedAttachment(ChunkAccess chunk, BlockPos pos) {
        this.chunk = chunk;
        this.pos = pos;
    }

    public StuntedAttachment(Level level, BlockPos pos) {
        this(level.getChunk(pos), pos);
    }

    public boolean isStunted() {
        final var attachment = chunk.getAttached(TYPE);
        return attachment != null && attachment.contains(pos);
    }

    public void stunt() {
        final var attachment = chunk.getAttachedOrCreate(TYPE);
        attachment.add(pos);
        chunk.setAttached(TYPE, attachment);
    }

    public void promote() {
        final var attachment = chunk.getAttached(TYPE);
        if (attachment != null) {
            attachment.remove(pos);
            chunk.setAttached(TYPE, attachment);
        }
    }

    public static void init() {}
}
