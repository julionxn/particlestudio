package net.pulga22.particlestudio;

import net.fabricmc.api.ClientModInitializer;
import net.pulga22.particlestudio.networking.AllPackets;

public class ParticleStudioClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AllPackets.registerS2CPackets();
    }



}
