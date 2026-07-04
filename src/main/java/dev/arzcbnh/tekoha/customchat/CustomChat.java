package dev.arzcbnh.tekoha.customchat;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class CustomChat implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(CustomChatCommands::registerCustomColorCommand);
    }
}
