package dev.arzcbnh.tekoha.customchat;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import java.util.Objects;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class CustomChatCommands {
    public static void registerCustomColorCommand(
            CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext buildContext,
            Commands.CommandSelection selection) {
        dispatcher.register(Commands.literal("tekoha")
                .then(Commands.literal("custom-color")
                        .requires(CommandSourceStack::isPlayer)
                        .then(Commands.literal("clear").executes(CustomChatCommands::clearColorCallback))
                        .then(Commands.literal("set")
                                .then(Commands.argument("color", StringArgumentType.string())
                                        .executes(CustomChatCommands::setColorPackedCallback))
                                .then(Commands.argument("red", StringArgumentType.string())
                                        .then(Commands.argument("green", StringArgumentType.string())
                                                .then(Commands.argument("blue", StringArgumentType.string())
                                                        .executes(CustomChatCommands::setColorComponentsCallback)))))));
    }

    private static int clearColorCallback(CommandContext<CommandSourceStack> context) {
        final var source = context.getSource();
        final var uuid = Objects.requireNonNull(source.getPlayer()).getUUID();
        final var data = CustomChatData.getInstance(source.getServer());
        data.setColor(uuid, null);
        source.sendSuccess(() -> Component.translatable("tekoha.customchat.success.clear"), false);
        return 1;
    }

    private static int setColorPackedCallback(CommandContext<CommandSourceStack> context) {
        final var source = context.getSource();
        final int color;

        try {
            color = decodeColorArgument(context, "color", 0xFFFFFF);
        } catch (NumberFormatException e) {
            source.sendFailure(Component.translatable("tekoha.customchat.fail.decode"));
            return 0;
        } catch (IllegalArgumentException e) {
            source.sendFailure(Component.translatable("tekoha.customchat.fail.bounds", "#ffffff"));
            return 0;
        }

        return setColor(context.getSource(), color);
    }

    private static int setColorComponentsCallback(CommandContext<CommandSourceStack> context) {
        final var source = context.getSource();
        final int red, green, blue;

        try {
            red = decodeColorArgument(context, "red", 255);
            green = decodeColorArgument(context, "green", 255);
            blue = decodeColorArgument(context, "blue", 255);
        } catch (NumberFormatException e) {
            source.sendFailure(Component.translatable("tekoha.customchat.fail.decode"));
            return 0;
        } catch (IllegalArgumentException e) {
            source.sendFailure(Component.translatable("tekoha.customchat.fail.bounds", "255"));
            return 0;
        }

        return setColor(context.getSource(), (red << 16) | (green << 8) | blue);
    }

    private static int decodeColorArgument(CommandContext<CommandSourceStack> context, String name, int max) {
        final var arg = StringArgumentType.getString(context, name);
        final int value = Integer.decode(arg);

        if (value < 0 || value > max) {
            throw new IllegalArgumentException();
        }

        return value;
    }

    private static int setColor(CommandSourceStack source, int color) {
        final var uuid = Objects.requireNonNull(source.getPlayer()).getUUID();
        final var data = CustomChatData.getInstance(source.getServer());
        data.setColor(uuid, color);

        final var code = Component.literal(String.format("#%06x", color)).withColor(color);
        source.sendSuccess(() -> Component.translatable("tekoha.customchat.success.set", code), false);
        return 1;
    }
}
