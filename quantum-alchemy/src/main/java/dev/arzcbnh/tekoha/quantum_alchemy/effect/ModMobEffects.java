package dev.arzcbnh.tekoha.effect;

import dev.arzcbnh.tekoha.TekohaAdditions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.CommonColors;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;

import java.util.function.Supplier;

public class ModMobEffects implements ModInitializer {
    public static final Holder<MobEffect> RECALL = register("recall", new RecallMobEffect());
//    public static final ResourceKey<ChatType> TEKOHA_CHAT = ResourceKey.create(Registries.CHAT_TYPE, Identifier.fromNamespaceAndPath(TekohaAdditions.MOD_ID, "text"));

    private static Holder<MobEffect> register(String id, MobEffect effect) {
        return Registry.registerForHolder(BuiltInRegistries.MOB_EFFECT, Identifier.fromNamespaceAndPath(TekohaAdditions.MOD_ID, id), effect);
//        final <MobEffect> key = Holder.direct(Registries.MOB_EFFECT, Identifier.fromNamespaceAndPath(TekohaAdditions.MOD_ID, id));
//        final StatusEffect statusEffect = factory.get();

//        return Registry.registerReference(Registries.STATUS_EFFECT, key, statusEffect);
    }

    @Override
    public void onInitialize() {
//        ServerPlayerEvents.JOIN.register(player -> player.sendChatMessage(OutgoingChatMessage.create(PlayerChatMessage.unsigned(player.getUUID(), "<someone> oh")), false, ChatType.bind(ChatType.CHAT, player.registryAccess(), ((Entity) player).getDisplayName())));
//        System.out.println("PORRAAAA");
    }
}
