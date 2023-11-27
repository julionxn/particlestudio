package net.pulga22.particlestudio.core.routines;

import java.io.Serializable;

public class ParticlePoint implements Serializable {

    public final float[] position = {0, 0, 0};
    public final String particleType;

    public ParticlePoint(String particleType, float x, float y, float z){
        position[0] = x;
        position[1] = y;
        position[2] = z;
        this.particleType = particleType;
    }

    public void spawnParticle(){
        System.out.println("Spawn");
    }

}
