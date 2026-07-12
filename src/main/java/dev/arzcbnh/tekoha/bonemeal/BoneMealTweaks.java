package dev.arzcbnh.tekoha.bonemeal;

import net.fabricmc.api.ModInitializer;

public class BoneMealTweaks implements ModInitializer {
    @Override
    public void onInitialize() {
        StuntedAttachment.init();
    }
}
