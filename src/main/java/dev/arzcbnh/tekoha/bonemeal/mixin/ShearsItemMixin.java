package dev.arzcbnh.tekoha.bonemeal.mixin;

import dev.arzcbnh.tekoha.bonemeal.StuntedAttachment;
import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.VineBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearsItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        final var level = context.getLevel();
        final var pos = context.getClickedPos();
        final var block = level.getBlockState(pos).getBlock();

        if (!(block instanceof VineBlock
                || block instanceof SugarCaneBlock
                || block instanceof BambooSaplingBlock
                || block instanceof BambooStalkBlock)) {
            return;
        }

        final var attachment = new StuntedAttachment(level, pos);

        if (attachment.isStunted()) {
            return;
        }

        final var player = context.getPlayer();
        final var itemInHand = context.getItemInHand();

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger((ServerPlayer) player, pos, itemInHand);
        }

        if (player != null) {
            itemInHand.hurtAndBreak(1, player, context.getHand().asEquipmentSlot());
        }

        attachment.stunt();
        level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
        cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
    }
}
