package net.pulga22.particlestudio.networking.packets.save;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;

import java.util.UUID;

public class C2SSendRoutineSaveChunk {


    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {

        int index = buf.readInt();
        boolean end = buf.readBoolean();
        UUID routineUUID = buf.readUuid();
        byte[] data = buf.readByteArray();
        server.execute(() -> ParticleRoutinesManager.getInstance().save(index, end, routineUUID, data));

    }
}
