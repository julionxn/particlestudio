package net.pulga22.particlestudio.networking.packets.sync;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.UUID;

public class S2CReceiveRoutineChunk {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;
        int index = buf.readInt();
        boolean end = buf.readBoolean();
        UUID routineUUID = buf.readUuid();
        byte[] data = buf.readByteArray();
        client.execute(() -> {
            PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
            accessor.particlestudio$getEditor().loadChunk(index, end, routineUUID, data);
        });

    }
}
