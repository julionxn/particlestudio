package net.pulga22.particlestudio.networking.packets.sync;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.UUID;

public class S2CResponseRoutineSync {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;

        UUID uuid = buf.readUuid();
        String name = buf.readString();
        int hashRoutine = buf.readInt();
        client.execute(() -> {
            PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
            PlayerEditor playerEditor = accessor.particlestudio$getEditor();
            playerEditor.getRoutine(uuid).ifPresentOrElse(routine -> {
                if (routine.hashCode() == hashRoutine) {
                    playerEditor.setActiveRoutine(routine);
                    playerEditor.openEditor();
                    ParticleStudio.LOGGER.info("Routine " + routine.name + " loaded from cache.");
                    return;
                }
                requestChunks(playerEditor, uuid, name);
            }, () -> requestChunks(playerEditor, uuid, name));
        });
    }

    public static void requestChunks(PlayerEditor playerEditor, UUID uuid, String name){
        playerEditor.prepareToLoad(uuid);
        ClientPlayNetworking.send(AllPackets.C2S_REQUEST_ROUTINE_CHUNKS, PacketByteBufs.create().writeUuid(uuid).writeString(name));
    }

}
