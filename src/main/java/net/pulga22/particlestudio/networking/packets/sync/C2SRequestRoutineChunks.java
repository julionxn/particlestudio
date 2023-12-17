package net.pulga22.particlestudio.networking.packets.sync;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;
import net.pulga22.particlestudio.core.routines.managers.WorldRoutines;
import net.pulga22.particlestudio.networking.AllPackets;

import java.util.UUID;

public class C2SRequestRoutineChunks {
    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {

        UUID uuid = buf.readUuid();
        String name = buf.readString();
        server.execute(() -> {
            WorldRoutines worldRoutines = ParticleRoutinesManager.getInstance().getRoutines(player.getWorld());
            if (worldRoutines == null) return;
            worldRoutines.getRoutine(name).flatMap(Routine::serializeInChunks).ifPresent(data -> {
                for (int i = 0; i < data.length; i++) {
                    byte[] datum = data[i];
                    PacketByteBuf newBuf = PacketByteBufs.create();
                    buf.writeInt(i);
                    buf.writeBoolean(i == data.length - 1).writeUuid(uuid);
                    buf.writeByteArray(datum);
                    ClientPlayNetworking.send(AllPackets.S2C_RECEIVE_ROUTINE_CHUNK, newBuf);
                }
            });
        });

    }
}
