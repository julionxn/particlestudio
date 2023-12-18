package net.pulga22.particlestudio.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.networking.packets.C2SRequestParticleControllerScreen;
import net.pulga22.particlestudio.networking.packets.S2COpenParticleControllerScreen;
import net.pulga22.particlestudio.networking.packets.names.C2SRequestRoutinesNames;
import net.pulga22.particlestudio.networking.packets.names.S2CReceiveRoutinesNames;
import net.pulga22.particlestudio.networking.packets.save.C2SRequestRoutineSave;
import net.pulga22.particlestudio.networking.packets.save.C2SSendRoutineSaveChunk;
import net.pulga22.particlestudio.networking.packets.save.S2CResponseRoutineSave;
import net.pulga22.particlestudio.networking.packets.sync.C2SRequestRoutineChunks;
import net.pulga22.particlestudio.networking.packets.sync.C2SRequestRoutineSync;
import net.pulga22.particlestudio.networking.packets.sync.S2CReceiveRoutineChunk;
import net.pulga22.particlestudio.networking.packets.sync.S2CResponseRoutineSync;

import java.util.HashMap;

public class AllPackets {

    private static final HashMap<Identifier, ServerPlayNetworking.PlayChannelHandler> C2SEntries = new HashMap<>();
    private static final HashMap<Identifier, ClientPlayNetworking.PlayChannelHandler> S2CEntries = new HashMap<>();

    public static final Identifier C2S_REQUEST_PARTICLE_CONTROLLER_SCREEN = c2s("request_particle_controller_screen", C2SRequestParticleControllerScreen::onServer);
    public static final Identifier S2C_OPEN_PARTICLE_CONTROLLER_SCREEN = s2c("open_particle_controller_screen", S2COpenParticleControllerScreen::onClient);
    public static final Identifier C2S_REQUEST_ROUTINES_NAMES = c2s("request_routines_names", C2SRequestRoutinesNames::onServer);
    public static final Identifier S2C_RECEIVE_ROUTINES_NAMES = s2c("receive_routines_names", S2CReceiveRoutinesNames::onClient);
    public static final Identifier C2S_REQUEST_ROUTINE_SYNC = c2s("request_routine_sync", C2SRequestRoutineSync::onServer);
    public static final Identifier S2C_RESPONSE_ROUTINE_SYNC = s2c("response_routine_sync", S2CResponseRoutineSync::onClient);
    public static final Identifier C2S_REQUEST_ROUTINE_CHUNKS = c2s("request_routine_chunks", C2SRequestRoutineChunks::onServer);
    public static final Identifier S2C_RECEIVE_ROUTINE_CHUNK = s2c("receive_routine_chunk", S2CReceiveRoutineChunk::onClient);
    public static final Identifier C2S_REQUEST_ROUTINE_SAVE = c2s("request_routine_save", C2SRequestRoutineSave::onServer);
    public static final Identifier S2C_RESPONSE_ROUTINE_SAVE = s2c("response_routine_save", S2CResponseRoutineSave::onClient);
    public static final Identifier C2S_SEND_ROUTINE_SAVE_CHUNK = c2s("send_routine_save_chunk", C2SSendRoutineSaveChunk::onServer);

    private static Identifier c2s(String packetName, ServerPlayNetworking.PlayChannelHandler channelHandler){
        Identifier identifier = new Identifier(ParticleStudio.MOD_ID, packetName);
        C2SEntries.put(identifier, channelHandler);
        return identifier;
    }

    private static Identifier s2c(String packetName, ClientPlayNetworking.PlayChannelHandler channelHandler){
        Identifier identifier = new Identifier(ParticleStudio.MOD_ID, packetName);
        S2CEntries.put(identifier, channelHandler);
        return identifier;
    }

    public static void registerC2SPackets(){
        C2SEntries.forEach(ServerPlayNetworking::registerGlobalReceiver);
    }

    public static void registerS2CPackets(){
        S2CEntries.forEach(ClientPlayNetworking::registerGlobalReceiver);
    }

}
