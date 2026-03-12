package dev.arzcbnh.tekoha.block;

import dev.arzcbnh.tekoha.TekohaAdditions;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Function;

public class ModBlocks implements ModInitializer {
//    public static final Block SNIPPED_VINE = register("snipped_vine", SnippedVineBlock::new, BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).replaceable().noCollision().strength(0.2F).sound(SoundType.VINE).ignitedByLava().pushReaction(PushReaction.DESTROY));

    private static Block register(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings) {
        final var id = Identifier.fromNamespaceAndPath(TekohaAdditions.MOD_ID, name);
        final var block = factory.apply(settings.setId(ResourceKey.create(Registries.BLOCK, id)));
        return Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    @Override
    public void onInitialize() {}
}
