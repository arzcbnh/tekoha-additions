package dev.arzcbnh.tekoha.potion;

import dev.arzcbnh.tekoha.TekohaAdditions;
import dev.arzcbnh.tekoha.effect.ModMobEffects;
import eu.pb4.polymer.core.api.other.SimplePolymerPotion;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

public class ModPotions implements ModInitializer {
    public static final Holder<Potion> PHASING = register("phasing", new SimplePolymerPotion("phasing"));
    public static final Holder<Potion> RECALL = register("recall", new SimplePolymerPotion("recall", new MobEffectInstance(ModMobEffects.RECALL)));

    private static Holder<Potion> register(String id, Potion potion) {
        return Registry.registerForHolder(BuiltInRegistries.POTION, Identifier.fromNamespaceAndPath(TekohaAdditions.MOD_ID, id), potion);
    }

    @Override
    public void onInitialize() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(builder -> {
            builder.registerPotionRecipe(Potions.WATER, Ingredient.of(Items.ENDER_PEARL), ModPotions.PHASING);
            builder.registerPotionRecipe(ModPotions.PHASING, Ingredient.of(Items.NAUTILUS_SHELL), ModPotions.RECALL);
//            builder.registerPotionRecipe(ModPotions.PHASING, Items.RECOVERY_COMPASS, ModPotions.RECOVERY);
//            builder.registerPotionRecipe(ModPotions.PHASING, ModItems.QUANTUM_THREAD, ModPotions.WORMHOLE);
        });
    }
}
