package net.pulga22.particlestudio.networking.packets.save;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;
import net.pulga22.particlestudio.networking.AllPackets;

import java.util.UUID;

public class C2SRequestRoutineSave {
    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {

        UUID uuid = buf.readUuid();
        server.execute(() -> {
            ParticleRoutinesManager.getInstance().prepareToSaveChunks(uuid);
            PacketByteBuf newBuf = PacketByteBufs.create().writeUuid(uuid);
            ServerPlayNetworking.send(player, AllPackets.S2C_RESPONSE_ROUTINE_SAVE, newBuf);
        });

    }
}
