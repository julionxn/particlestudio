package net.pulga22.particlestudio.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.pulga22.particlestudio.core.editor.screen.gui.RoutineSelectionMenu;

public class S2COpenParticleControllerScreen {
    public static void onClient(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {

        PlayerEntity player = client.player;
        if (player == null) return;
        int hashWorld = buf.readInt();
        client.execute(() -> {
            client.setScreen(new RoutineSelectionMenu(player, hashWorld));
        });

    }
}
