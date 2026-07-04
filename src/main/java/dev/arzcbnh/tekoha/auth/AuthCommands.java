package dev.arzcbnh.tekoha.auth;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.permissions.Permissions;

public class AuthCommands {
    public static void registerAuthCommand(
            CommandDispatcher<CommandSourceStack> dispatcher,
            CommandBuildContext buildContext,
            Commands.CommandSelection selection) {
        dispatcher.register(Commands.literal("tekoha")
                .then(Commands.literal("auth")
                        .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_OWNER))
                        .then(Commands.literal("set")
                                .then(Commands.argument("player", StringArgumentType.word())
                                        .then(Commands.argument("token", StringArgumentType.greedyString())
                                                .executes(AuthCommands::setTokenCallback))))
                        .then(Commands.literal("clear")
                                .then(Commands.argument("player", GameProfileArgument.gameProfile())
                                        .executes(AuthCommands::clearTokenCallback)))));
    }

    private static int setTokenCallback(CommandContext<CommandSourceStack> context) {
        final var source = context.getSource();
        final var name = StringArgumentType.getString(context, "player");
        final var token = StringArgumentType.getString(context, "token");
        final var data = AuthData.getInstance(source.getServer());

        data.setToken(UUIDUtil.createOfflinePlayerUUID(name), token);
        source.sendSuccess(() -> Component.translatable("tekoha.auth.set.success"), true);
        return 1;
    }

    private static int clearTokenCallback(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        final var source = context.getSource();
        final var profiles = GameProfileArgument.getGameProfiles(context, "player");
        final var data = AuthData.getInstance(source.getServer());

        for (var player : profiles) {
            data.setToken(UUIDUtil.createOfflinePlayerUUID(player.name()), null);
        }

        source.sendSuccess(() -> Component.translatable("tekoha.auth.clear.success"), true);
        return 1;
    }
}
