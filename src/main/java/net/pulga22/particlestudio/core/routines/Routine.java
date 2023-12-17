package net.pulga22.particlestudio.core.routines;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.handlers.PointSelector;
import net.pulga22.particlestudio.core.editor.handlers.SelectionHandler;
import net.pulga22.particlestudio.core.routines.paths.Path;

import java.io.*;
import java.util.List;
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

    public void render(WorldRenderContext context, List<ParticlePoint> selectedPoints){
        if (timeline.isEmpty() || (routinePlayer != null && routinePlayer.isPlaying())) return;
        renderPoints(context, selectedPoints);
        renderPaths(context);
    }

    private void renderPaths(WorldRenderContext context){
        if (editingPath == null) return;
        editingPath.render(context);
    }

    private void renderPoints(WorldRenderContext context, List<ParticlePoint> selectedPoints){
        int selectedTick = timeline.getCurrentEditingTick();
        int lower = timeline.onionLowerBound();
        int upper = timeline.onionUpperBound();
        double sigma = Math.min(selectedTick - lower, upper - selectedTick) / 2.0;
        double sigmaSquared = sigma * sigma;
        for (int tick = lower; tick <= upper; tick++) {
            List<ParticlePoint> points = timeline.getPointsOfTick(tick);
            points.forEach(particlePoint -> {
                if (selectedPoints.contains(particlePoint)){
                    PointRenderer.renderSelectedPoint(context, particlePoint.getPosition());
                    return;
                }
                float gradient = (float) generateGradient(particlePoint.tick, selectedTick, sigmaSquared);
                PointRenderer.renderParticlePoint(context, particlePoint.getPosition(), gradient);
            });
        }
    }

    private double generateGradient(int tick, int editing, double sigmaSquared){
        double exponent = -Math.pow((tick - editing), 2) / (2 * sigmaSquared);
        return 1 - Math.pow(Math.E, exponent);
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

    public static Optional<byte[][]> serialize(Routine routine){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(routine);
            byte[] array = bos.toByteArray();
            int chunkSize = 1024;
            int chunksAmount = (int) Math.ceil((double) array.length / chunkSize);
            byte[][] result = new byte[chunksAmount][];
            for (int i = 0; i < chunksAmount; i++) {
                int start = i * chunkSize;
                int length = Math.min(array.length - start, chunkSize);
                result[i] = new byte[length];
                System.arraycopy(array, start, result[i], 0, length);
            }
            return Optional.of(result);
        } catch (IOException e) {
            return Optional.empty();
        }
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
