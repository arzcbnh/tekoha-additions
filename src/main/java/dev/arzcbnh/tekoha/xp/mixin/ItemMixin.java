package dev.arzcbnh.tekoha.xp.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.arzcbnh.tekoha.xp.PlayerMixinAccess;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Item.class)
public class ItemMixin {
    @WrapMethod(method = "use")
    private InteractionResult tekoha$mendFromPlayerXp(
            Level level, Player player, InteractionHand hand, Operation<InteractionResult> original) {
        final var result = original.call(level, player, hand);
        final var stack = player.getItemInHand(hand);
        final boolean hasMending =
                stack.getEnchantments().keySet().contains(level.registryAccess().getOrThrow(Enchantments.MENDING));

        if (result != InteractionResult.PASS || !player.isCrouching() || !stack.isDamaged() || !hasMending) {
            return result;
        }

        final var access = (PlayerMixinAccess) player;
        final int xp = Math.max(access.tekoha$getProgressXpPoints(), access.tekoha$getLevelXpPoints());
        final int toRepairFromXp = EnchantmentHelper.modifyDurabilityToRepairFromXp((ServerLevel) level, stack, xp);
        final int repair = Math.min(toRepairFromXp, stack.getDamageValue());

        stack.setDamageValue(stack.getDamageValue() - repair);
        player.giveExperiencePoints(-repair * xp / toRepairFromXp);
        return InteractionResult.SUCCESS_SERVER;
    }
}
