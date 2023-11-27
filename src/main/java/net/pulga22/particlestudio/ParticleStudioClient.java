package net.pulga22.particlestudio;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.pulga22.particlestudio.core.editor.screen.hud.EditorHud;
import net.pulga22.particlestudio.networking.AllPackets;

public class ParticleStudioClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AllPackets.registerS2CPackets();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client == null) return;
            ClientPlayNetworking.send(AllPackets.C2S_REQUEST_ROUTINES_NAMES, PacketByteBufs.empty());
        });
        HudRenderCallback.EVENT.register(new EditorHud());
    }



}
