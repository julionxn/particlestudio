package net.pulga22.particlestudio.networking.packets.names;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.HashSet;
import java.util.Set;

public class S2CReceiveRoutinesNames {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;
        Set<String> names = buf.readCollection(HashSet::new, PacketByteBuf::readString);
        client.execute(() -> {
            PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
            accessor.particlestudio$getEditor().setRoutineNames(names);
        });

    }
}
