package net.pulga22.particlestudio.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.networking.AllPackets;

public class C2SRequestParticleControllerScreen {
    public static void onServer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {

        server.execute(() -> {
            int hashWorld = player.getWorld().hashCode();
            ServerPlayNetworking.send(player, AllPackets.S2C_OPEN_PARTICLE_CONTROLLER_SCREEN, PacketByteBufs.create().writeInt(hashWorld));
        });

    }
}
