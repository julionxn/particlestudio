package net.pulga22.particlestudio.core.routines.managers;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class ParticleRoutinesManager {

    private static ParticleRoutinesManager INSTANCE;
    private final HashMap<String, ParticleType<?>> particles = new HashMap<>();
    private final List<String> particlesIds = new ArrayList<>();
    private final HashMap<World, WorldRoutines> routines = new HashMap<>();
    private int particlesAmount;

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

    public void prepareParticles(){
        for (ParticleType<?> particleType : Registries.PARTICLE_TYPE) {
            ParticleRoutinesManager.getInstance().registerParticle(particleType);
        }
        particlesAmount = particles.size();
        particlesIds.addAll(particles.keySet().stream().sorted().toList());
    }

    public List<String> getAllParticleIds(){
        return particlesIds;
    }

    public int getParticlesAmount(){
        return particlesAmount;
    }

    public Optional<ParticleType<?>> getParticleType(String id){
        if (particles.containsKey(id)) return Optional.of(particles.get(id));
        return Optional.empty();
    }

}
