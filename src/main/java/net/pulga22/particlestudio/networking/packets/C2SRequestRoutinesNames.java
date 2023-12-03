package net.pulga22.particlestudio.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;
import net.pulga22.particlestudio.core.routines.managers.WorldRoutines;
import net.pulga22.particlestudio.networking.AllPackets;

import java.util.Set;

public class C2SRequestRoutinesNames {
    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf bufN, PacketSender sender) {

        server.execute(() -> {
            WorldRoutines routines = ParticleRoutinesManager.getInstance().getRoutines(player.getWorld());
            if (routines == null) return;
            Set<String> names = routines.routines.keySet();
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeCollection(names, PacketByteBuf::writeString);
            ServerPlayNetworking.send(player, AllPackets.S2C_RECEIVE_ROUTINES_NAMES, buf);
        });

    }
}
