package net.pulga22.particlestudio.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class S2CToggleEditingMode {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        boolean newState = buf.readBoolean();

        client.execute(() -> {
            ClientPlayerEntity player = client.player;
            if (player == null) return;
            ((PlayerEntityAccessor) player).particlestudio$setEditing(newState);
        });

    }
}
