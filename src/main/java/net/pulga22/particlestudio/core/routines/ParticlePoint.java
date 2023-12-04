package net.pulga22.particlestudio.core.routines;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class ParticlePoint implements Serializable {

    public final double[] position = {0, 0, 0};
    public final String particleType;

    public ParticlePoint(String particleType, double x, double y, double z) {
        position[0] = x;
        position[1] = y;
        position[2] = z;
        this.particleType = particleType;
    }

    public void spawnParticle(World world) {
        Optional<ParticleType<?>> particleTypeOptional = ParticleRoutinesManager.getInstance().getParticleType(particleType);
        particleTypeOptional.ifPresent(particle -> {
            world.addParticle((ParticleEffect) particle, position[0], position[1], position[2], 0, 0, 0);
        });
    }

    public Vec3d getPosition(){
        return new Vec3d(position[0], position[1], position[2]);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticlePoint that = (ParticlePoint) o;
        return Arrays.equals(position, that.position) && Objects.equals(particleType, that.particleType);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(particleType);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }
}
