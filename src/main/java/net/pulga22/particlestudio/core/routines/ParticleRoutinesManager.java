package net.pulga22.particlestudio.core.routines;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class ParticleRoutinesManager {

    private static final HashMap<String, ParticleType<?>> particles = new HashMap<>();
    private static ParticleRoutinesManager INSTANCE;
    private final HashMap<World, WorldRoutines> routines = new HashMap<>();

    public static ParticleRoutinesManager getInstance(){
        if (INSTANCE == null) INSTANCE = new ParticleRoutinesManager();
        return INSTANCE;
    }

    @Nullable
    public WorldRoutines getRoutines(World world){
        return this.routines.get(world);
    }

    public void loadWorld(World world, WorldRoutines worldRoutines){
        System.out.println("Registering world: " + world.hashCode());
        this.routines.put(world, worldRoutines);
    }

    public void registerParticle(ParticleType<?> particleType){
        Identifier identifier = Registries.PARTICLE_TYPE.getId(particleType);
        if (identifier == null) return;
        particles.put(identifier.toString(), particleType);
    }

}
