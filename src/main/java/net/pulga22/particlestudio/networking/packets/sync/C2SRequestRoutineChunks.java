package net.pulga22.particlestudio.networking.packets.sync;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;
import net.pulga22.particlestudio.networking.AllPackets;

import java.util.UUID;

public class C2SRequestRoutineChunks {
    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {

        UUID uuid = buf.readUuid();
        String name = buf.readString();
        server.execute(() ->
            ParticleRoutinesManager.getInstance().getRoutines(player.getWorld())
                    .flatMap(worldRoutines -> worldRoutines.getRoutine(name)
                    .flatMap(Routine::serializeInChunks)).ifPresent(data -> {
                        for (int i = 0; i < data.length; i++) {
                            byte[] datum = data[i];
                            PacketByteBuf newBuf = PacketByteBufs.create();
                            newBuf.writeInt(i);
                            newBuf.writeInt(data.length).writeUuid(uuid);
                            newBuf.writeByteArray(datum);
                            ServerPlayNetworking.send(player, AllPackets.S2C_RECEIVE_ROUTINE_CHUNK, newBuf);
                }
            })
        );

    }
}
