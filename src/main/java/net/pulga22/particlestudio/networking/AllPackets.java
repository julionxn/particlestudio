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

public class AllPackets {

    public static final Identifier C2S_REQUEST_PARTICLE_CONTROLLER_SCREEN = of("request_particle_controller_screen");
    public static final Identifier S2C_OPEN_PARTICLE_CONTROLLER_SCREEN = of("open_particle_controller_screen");
    public static final Identifier C2S_REQUEST_ROUTINES_NAMES = of("request_routines_names");
    public static final Identifier S2C_RECEIVE_ROUTINES_NAMES = of("receive_routines_names");
    public static final Identifier C2S_REQUEST_ROUTINE_SYNC = of("request_routine_sync");
    public static final Identifier S2C_RESPONSE_ROUTINE_SYNC = of("response_routine_sync");
    public static final Identifier C2S_REQUEST_ROUTINE_CHUNKS = of("request_routine_chunks");
    public static final Identifier S2C_RECEIVE_ROUTINE_CHUNK = of("receive_routine_chunk");
    public static final Identifier C2S_REQUEST_ROUTINE_SAVE = of("request_routine_save");
    public static final Identifier S2C_RESPONSE_ROUTINE_SAVE = of("response_routine_save");
    public static final Identifier C2S_SEND_ROUTINE_SAVE_CHUNK = of("send_routine_save_chunk");

    private static Identifier of(String packetName){
        return new Identifier(ParticleStudio.MOD_ID, packetName);
    }

    public static void registerS2CPackets(){
        s2c(S2C_OPEN_PARTICLE_CONTROLLER_SCREEN, S2COpenParticleControllerScreen::onClient);
        s2c(S2C_RECEIVE_ROUTINES_NAMES, S2CReceiveRoutinesNames::onClient);
        s2c(S2C_RESPONSE_ROUTINE_SYNC, S2CResponseRoutineSync::onClient);
        s2c(S2C_RECEIVE_ROUTINE_CHUNK, S2CReceiveRoutineChunk::onClient);
        s2c(S2C_RESPONSE_ROUTINE_SAVE, S2CResponseRoutineSave::onClient);
    }

    public static void registerC2SPackets(){
        c2s(C2S_REQUEST_PARTICLE_CONTROLLER_SCREEN, C2SRequestParticleControllerScreen::onServer);
        c2s(C2S_REQUEST_ROUTINES_NAMES, C2SRequestRoutinesNames::onServer);
        c2s(C2S_REQUEST_ROUTINE_SYNC, C2SRequestRoutineSync::onServer);
        c2s(C2S_REQUEST_ROUTINE_CHUNKS, C2SRequestRoutineChunks::onServer);
        c2s(C2S_REQUEST_ROUTINE_SAVE, C2SRequestRoutineSave::onServer);
        c2s(C2S_SEND_ROUTINE_SAVE_CHUNK, C2SSendRoutineSaveChunk::onServer);
    }

    private static void c2s(Identifier identifier, ServerPlayNetworking.PlayChannelHandler handler){
        ServerPlayNetworking.registerGlobalReceiver(identifier, handler);
    }

    private static void s2c(Identifier identifier, ClientPlayNetworking.PlayChannelHandler handler){
        ClientPlayNetworking.registerGlobalReceiver(identifier, handler);
    }

}
