package dev.arzcbnh.tekoha.xp.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.arzcbnh.tekoha.xp.PlayerMixinAccess;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BottleItem.class)
public abstract class BottleItemMixin {
    @Shadow
    protected abstract ItemStack turnBottleIntoItem(ItemStack itemStack, Player player, ItemStack itemStackToTurnInto);

    @WrapMethod(method = "use")
    private InteractionResult tekoha$storeXpInBottle(
            Level level, Player player, InteractionHand hand, Operation<InteractionResult> original) {
        final var result = original.call(level, player, hand);
        final int xp = ((PlayerMixinAccess) player).tekoha$getTotalXp();

        if (result != InteractionResult.PASS || xp < 3) {
            return result;
        }

        final var stack = player.getItemInHand(hand);
        final var xpBottle = this.turnBottleIntoItem(stack, player, new ItemStack(Items.EXPERIENCE_BOTTLE));

        // https://minecraft.wiki/w/Bottle_o'_Enchanting#Usage but take one extra XP point as penalty
        player.giveExperiencePoints(xp < 11 ? -xp : level.getRandom().nextInt(-12, -3));
        return InteractionResult.SUCCESS_SERVER.heldItemTransformedTo(xpBottle);
    }
}
