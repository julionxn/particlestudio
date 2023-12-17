package net.pulga22.particlestudio.core.routines.managers;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.pulga22.particlestudio.ParticleStudio;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ParticleRoutinesManager {

    private static ParticleRoutinesManager INSTANCE;
    private final HashMap<String, ParticleType<?>> particles = new HashMap<>();
    private final List<String> particlesIds = new ArrayList<>();
    private final HashMap<UUID, PartialRoutine> routinesToSave = new HashMap<>();
    private final HashMap<Integer, WorldRoutines> worldRoutines = new HashMap<>();
    private int particlesAmount;

    private ParticleRoutinesManager() {}

    public static ParticleRoutinesManager getInstance(){
        if (INSTANCE == null) INSTANCE = new ParticleRoutinesManager();
        return INSTANCE;
    }

    @Nullable
    public WorldRoutines getRoutines(World world){
        return this.worldRoutines.get(world.hashCode());
    }

    public void loadWorld(World world, WorldRoutines worldRoutines){
        ParticleStudio.LOGGER.info("Registering world: " + world.hashCode());
        this.worldRoutines.put(world.hashCode(), worldRoutines);
    }

    private void registerParticle(ParticleType<?> particleType){
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
        return Optional.ofNullable(particles.get(id));
    }

    public void prepareToSave(UUID uuid){
        routinesToSave.put(uuid, new PartialRoutine(uuid));
    }

    public void save(int index, boolean end, UUID uuid, byte[] data){
        PartialRoutine routineToSave = routinesToSave.get(uuid);
        routineToSave.appendBytes(index, data);
        if (end){
            routineToSave.getRoutine().ifPresentOrElse(routine ->
                    worldRoutines.get(routine.belongsTo).setRoutine(routine.name, routine),
                () -> ParticleStudio.LOGGER.error("Something went wrong saving routine with UUID " + uuid));
        }
    }

}
