package net.pulga22.particlestudio.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.networking.packets.S2CToggleEditingMode;

import java.util.HashMap;

public class AllPackets {

    private static final HashMap<Identifier, ServerPlayNetworking.PlayChannelHandler> C2SEntries = new HashMap<>();
    private static final HashMap<Identifier, ClientPlayNetworking.PlayChannelHandler> S2CEntries = new HashMap<>();

    public static final Identifier S2C_TOGGLE_EDITING_MODE = s2c("toggle_editing_mode", S2CToggleEditingMode::onClient);


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
