package net.pulga22.particlestudio.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.Optional;

public class S2CReceiveRoutineSync {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;

        String name = buf.readString();
        Optional<Routine> routineOptional = Routine.deserialize(buf.readByteArray());
        routineOptional.ifPresent(routine -> {
            client.execute(() -> {
                PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
                accessor.particlestudio$getEditor().loadRoutine(name, routine);
                accessor.particlestudio$getEditor().setActiveRoutine(routine);
                accessor.particlestudio$setEditing(true);
            });
        });

    }
}
