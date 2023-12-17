package net.pulga22.particlestudio.networking.packets.save;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.UUID;

public class S2CResponseRoutineSave {


    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;
        UUID uuid = buf.readUuid();
        client.execute(() -> {
            PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
            PlayerEditor editor = accessor.particlestudio$getEditor();
            editor.getRoutine(uuid).ifPresent(routine -> routine.save(editor.getHandler()));
        });

    }
}
