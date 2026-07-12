package dev.arzcbnh.tekoha.bonemeal.mixin;

import dev.arzcbnh.tekoha.bonemeal.StuntedAttachment;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void useOn(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        final var level = (ServerLevel) context.getLevel();
        final var pos = context.getClickedPos();
        final var attachment = new StuntedAttachment(level, pos);

        if (!attachment.isStunted()) {
            return;
        }

        final var player = context.getPlayer();
        final var stack = context.getItemInHand();

        if (player != null) {
            stack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
        }

        // The code that generates bone meal success particles filters for BonemealableBlock, but it can't be mixed into
        // because it's client-side, so particles have to be generated manually here. The sound is triggered by the
        // level event regardless.
        level.levelEvent(1505, pos, 15);
        level.sendParticles(
                ParticleTypes.HAPPY_VILLAGER,
                pos.getX() + 0.5,
                pos.getY() + 0.5,
                pos.getZ() + 0.5,
                15,
                0.3,
                0.3,
                0.3,
                0);

        stack.shrink(1);
        attachment.promote();
        cir.setReturnValue(InteractionResult.SUCCESS_SERVER);
    }
}
