package net.pulga22.particlestudio.core.routines;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.handlers.PointSelector;
import net.pulga22.particlestudio.core.editor.handlers.SelectionHandler;
import net.pulga22.particlestudio.core.routines.paths.Path;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Routine implements Serializable {

    public final UUID uuid;
    public final String name;
    public final int belongsTo;
    private final Timeline timeline = new Timeline();
    private transient RoutinePlayer routinePlayer;
    private transient SelectionHandler selectionHandler;
    private transient Path editingPath;

    public Routine(UUID uuid, String name, int belongsTo){
        this.uuid = uuid;
        this.name = name;
        this.belongsTo = belongsTo;
    }

    public Timeline getTimeline(){
        return timeline;
    }

    public RoutinePlayer getRoutinePlayer(){
        if (routinePlayer == null) routinePlayer = new RoutinePlayer(this);
        return routinePlayer;
    }

    public SelectionHandler getSelectionHandler(){
        if (selectionHandler == null) selectionHandler = new SelectionHandler();
        return selectionHandler;
    }

    public void addParticlePoint(EditorHandler editorHandler){
        Vec3d pos = editorHandler.getCurrentPosition();
        int tick = timeline.getCurrentEditingTick();
        addParticlePoint(tick, new ParticlePoint(editorHandler.getCurrentParticle(), pos.x, pos.y, pos.z, tick));
    }

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        if (routinePlayer != null && routinePlayer.isPlaying()) routinePlayer.stop();
        timeline.addParticlePoint(time, particlePoint);
    }

    public void newPath(Path path){
        editingPath = path;
    }

    public void clearPath(){
        editingPath = null;
    }

    public Optional<Path> getEditingPath(){
        return Optional.ofNullable(editingPath);
    }

    public Optional<ParticlePoint> tryToSelectPoint(PlayerEntity player) {
        if (timeline.isEmpty()) return Optional.empty();
        return new PointSelector(
                timeline.getPoints().subList(timeline.onionLowerBound(), timeline.onionUpperBound() + 1), player)
                .getSelectedPoint();
    }

    public void prepareToSave(EditorHandler editorHandler){
        editorHandler.prepareSaveRoutine(this);
    }

    public void save(EditorHandler editorHandler){
        editorHandler.saveRoutine(this);
    }

    public static Optional<byte[]> serialize(Routine routine){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(routine);
            return Optional.of(baos.toByteArray());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    public static Optional<byte[][]> serializeInChunks(Routine routine){
        Optional<byte[]> optionalBytes = serialize(routine);
        if (optionalBytes.isEmpty()) return Optional.empty();
        byte[] routineBytes = optionalBytes.get();
        int chunkSize = 1024;
        if (routineBytes.length <= chunkSize) return Optional.of(new byte[][]{routineBytes});
        int chunksAmount = (int) Math.ceil((double) routineBytes.length / chunkSize);
        byte[][] result = new byte[chunksAmount][];
        for (int i = 0; i < chunksAmount; i++) {
            int start = i * chunkSize;
            int length = Math.min(routineBytes.length - start, chunkSize);
            result[i] = Arrays.copyOfRange(routineBytes, start, start + length);
        }
        return Optional.of(result);
    }

    public static Optional<Routine> deserialize(byte[] serializedRoutine){
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedRoutine);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return Optional.of((Routine) ois.readObject());
        } catch (IOException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Routine routine = (Routine) o;
        return Objects.equals(timeline, routine.timeline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeline);
    }
}
