package net.pulga22.particlestudio;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.pulga22.particlestudio.core.editor.screen.hud.DebugHud;
import net.pulga22.particlestudio.core.editor.screen.hud.EditorHud;
import net.pulga22.particlestudio.core.routines.ParticleClientTicker;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class ParticleStudioClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AllPackets.registerS2CPackets();
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (client == null) return;
            ClientPlayNetworking.send(AllPackets.C2S_REQUEST_ROUTINES_NAMES, PacketByteBufs.empty());
        });
        HudRenderCallback.EVENT.register(new EditorHud());
        HudRenderCallback.EVENT.register(new DebugHud());
        WorldRenderEvents.END.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) return;
            PlayerEntityAccessor accessor = (PlayerEntityAccessor) client.player;
            if (accessor == null || !accessor.particlestudio$isEditing()) return;
            accessor.particlestudio$getEditor().getCurrentRoutine().ifPresent(routine -> {
                routine.renderPreview(context);
            });
        });
        ClientTickEvents.END_WORLD_TICK.register(world -> {
            ParticleClientTicker.getInstance().tick(world);
        });
    }



}
