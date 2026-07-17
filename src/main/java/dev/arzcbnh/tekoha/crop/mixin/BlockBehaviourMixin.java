package dev.arzcbnh.tekoha.crop.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @Unique private final BlockBehaviour self = (BlockBehaviour) (Object) this;

    @WrapMethod(method = "useWithoutItem")
    private InteractionResult tekoha$rightClickHarvest(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult,
            Operation<InteractionResult> original) {
        if (self instanceof CropBlock && state.getValue(CropBlock.AGE) == CropBlock.MAX_AGE) {
            level.setBlockAndUpdate(pos, state.setValue(CropBlock.AGE, 0));
            Block.dropResources(state, level, pos);
            return InteractionResult.SUCCESS_SERVER;
        }

        return original.call(state, level, pos, player, hitResult);
    }
}
