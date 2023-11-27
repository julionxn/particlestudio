package net.pulga22.particlestudio.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.networking.packets.*;

import java.util.HashMap;

public class AllPackets {

    private static final HashMap<Identifier, ServerPlayNetworking.PlayChannelHandler> C2SEntries = new HashMap<>();
    private static final HashMap<Identifier, ClientPlayNetworking.PlayChannelHandler> S2CEntries = new HashMap<>();

    public static final Identifier S2C_TOGGLE_EDITING_MODE = s2c("toggle_editing_mode",
            S2CToggleEditingMode::onClient);
    public static final Identifier C2S_REQUEST_ROUTINES_NAMES = c2s("request_routines_names",
            C2SRequestRoutinesNames::onServer);
    public static final Identifier S2C_RECEIVE_ROUTINES_NAMES = s2c("receive_routines_names",
            S2CReceiveRoutinesNames::onClient);
    public static final Identifier C2S_REQUEST_ROUTINE_SYNC = c2s("request_routine_sync",
            C2SRequestRoutineSync::onServer);
    public static final Identifier S2C_RECEIVE_ROUTINE_SYNC = s2c("receive_routine_sync",
            S2CReceiveRoutineSync::onClient);

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
