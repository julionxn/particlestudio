package net.pulga22.particlestudio.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class ToggleEditingModeCommand {


    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register(
                CommandManager.literal("editor").executes(ToggleEditingModeCommand::toggleState)
        );
    }

    private static int toggleState(CommandContext<ServerCommandSource> ctx){
        ServerCommandSource src = ctx.getSource();
        if (src.isExecutedByPlayer()){
            ServerPlayerEntity player = src.getPlayer();
            if (player == null) return -1;
            boolean newState = !((PlayerEntityAccessor) player).particlestudio$isEditing();
            ((PlayerEntityAccessor) player).particlestudio$setEditing(newState);
            ServerPlayNetworking.send(player, AllPackets.S2C_TOGGLE_EDITING_MODE, PacketByteBufs.create().writeBoolean(newState));
        }
        return 1;
    }
}
