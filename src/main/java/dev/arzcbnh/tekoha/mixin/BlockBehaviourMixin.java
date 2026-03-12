package dev.arzcbnh.tekoha.mixin;

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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviourMixin {
    @Inject(method = "useWithoutItem", at = @At("HEAD"), cancellable = true)
    private void tekoha$useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (blockState.getBlock() instanceof CropBlock && blockState.getValue(CropBlock.AGE) == CropBlock.MAX_AGE) {
            level.setBlockAndUpdate(blockPos, blockState.setValue(CropBlock.AGE, 0));
            Block.dropResources(blockState, level, blockPos);
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
}
