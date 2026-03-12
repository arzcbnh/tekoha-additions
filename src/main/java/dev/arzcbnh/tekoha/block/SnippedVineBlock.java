package dev.arzcbnh.tekoha.block;

import com.mojang.serialization.MapCodec;
import eu.pb4.polymer.core.api.block.PolymerBlock;
import eu.pb4.polymer.core.api.block.SimplePolymerBlock;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import xyz.nucleoid.packettweaker.PacketContext;

public class SnippedVineBlock extends VineBlock implements PolymerBlock {
    public static final MapCodec<VineBlock> CODEC = simpleCodec(SnippedVineBlock::new);

    public MapCodec<VineBlock> codec() {
        return CODEC;
    }

    public SnippedVineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getPolymerBlockState(BlockState state, PacketContext context) {
        return this.defaultBlockState();
    }
}
