package net.pulga22.particlestudio.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.pulga22.particlestudio.core.routines.ParticleRoutinesManager;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.core.routines.WorldRoutines;
import net.pulga22.particlestudio.networking.AllPackets;

import java.util.Optional;

public class C2SRequestRoutineSync {
    public static void onServer(MinecraftServer server, ServerPlayerEntity player,
                                ServerPlayNetworkHandler serverPlayNetworkHandler, PacketByteBuf buf, PacketSender sender) {

        String name = buf.readString();
        server.execute(() -> {
            WorldRoutines worldRoutines = ParticleRoutinesManager.getInstance().getRoutines(player.getWorld());
            if (worldRoutines == null) return;
            Optional<Routine> routineOptional = worldRoutines.getRoutine(name);
            routineOptional.ifPresent(routine -> {
                byte[] serialized = Routine.serialize(routine);
                PacketByteBuf newBuf = PacketByteBufs.create().writeString(name).writeByteArray(serialized);
                ServerPlayNetworking.send(player, AllPackets.S2C_RECEIVE_ROUTINE_SYNC, newBuf);
            });
        });
    }
}
