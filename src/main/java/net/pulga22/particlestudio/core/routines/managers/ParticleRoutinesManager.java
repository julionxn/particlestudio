package net.pulga22.particlestudio.core.routines.managers;

import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.routines.Routine;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

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

    public void prepareToSaveChunks(UUID uuid){
        routinesToSave.put(uuid, new PartialRoutine(uuid));
    }

    public void saveChunk(int index, boolean end, UUID uuid, byte[] data){
        PartialRoutine routineToSave = routinesToSave.get(uuid);
        routineToSave.appendBytes(index, data);
        if (end){
            routineToSave.getRoutine().ifPresentOrElse(routine ->
                    worldRoutines.get(routine.belongsTo).setRoutine(routine.name, routine),
                () -> ParticleStudio.LOGGER.error("Something went wrong saving routine with UUID " + uuid));
            routinesToSave.remove(uuid);
        }
    }

    public void loadWorld(ServerWorld world){
        resolvePath(world).ifPresent(path -> {
            try (Stream<Path> walk = Files.walk(path)){
                WorldRoutines worldRoutine = new WorldRoutines();
                List<Path> routinePaths = walk.filter(Files::isRegularFile)
                        .filter(p -> p.toString().toLowerCase().endsWith(".rtn"))
                        .toList();
                for (Path routinePath : routinePaths) {
                    try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(routinePath))) {
                        Routine routine = (Routine) ois.readObject();
                        worldRoutine.setRoutine(routine.name, routine);
                        ParticleStudio.LOGGER.info("Routine " + routine.name + " loaded.");
                    }
                }
                worldRoutines.put(((World) world).hashCode(), worldRoutine);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void saveWorld(ServerWorld world){
        resolvePath(world).ifPresent(path -> {
            path.toFile().mkdir();
            WorldRoutines worldRoutine = worldRoutines.get(world.hashCode());
            worldRoutine.getRoutines().forEach(routine -> {
                saveRoutine(path, routine);
            });
        });
    }

    private void saveRoutine(Path path, Routine routine){
        Path filePath = path.resolve(routine.name + ".rtn");
        try (FileOutputStream fos = new FileOutputStream(filePath.toString());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(routine);
            ParticleStudio.LOGGER.info("Routine " + routine.name + " saved.");
        } catch (IOException e) {
            ParticleStudio.LOGGER.error("Failed to save routine " + routine.name + ".", e);
        }
    }

    public Optional<Path> resolvePath(ServerWorld world){
        Pattern pattern = Pattern.compile("\\\\saves\\\\([^\\\\]+)\\\\data");
        File file = world.getPersistentStateManager().directory;
        Matcher matcher = pattern.matcher(file.getAbsolutePath());
        if (!matcher.find()) return Optional.empty();
        Path path = file.toPath().resolve("routines");
        return Optional.of(path);
    }

}
