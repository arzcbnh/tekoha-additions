package dev.arzcbnh.tekoha.mixin;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Enchantments.class)
public class EnchantmentsMixin {
//    @Inject(method = "Lnet/minecraft/world/item/enchantment/Enchantments;register(Lnet/minecraft/data/worldgen/BootstrapContext;Lnet/minecraft/resources/ResourceKey;Lnet/minecraft/world/item/enchantment/Enchantment$Builder;)V", at = @At("HEAD"), cancellable = true)
//    private static void tekoha$register(BootstrapContext<Enchantment> bootstrapContext, ResourceKey<Enchantment> resourceKey, Enchantment.Builder builder, CallbackInfo ci) {
//        System.out.println("TRYING TO AVOID MENDING");
//        if (resourceKey == Enchantments.MENDING) {
//            System.out.println("ACTUALLY AVOIDING MENDING");
//            ci.cancel();
//        }
//    }
}
