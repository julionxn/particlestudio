package net.pulga22.particlestudio.core.routines;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.SelectionCalculator;
import net.pulga22.particlestudio.core.routines.paths.Path;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Routine implements Serializable {

    private final Timeline timeline = new Timeline();
    private transient RoutinePlayer routinePlayer = new RoutinePlayer(this);
    private transient Path editingPath;

    public Timeline getTimeline(){
        return timeline;
    }

    public RoutinePlayer getRoutinePlayer(){
        if (this.routinePlayer == null) this.routinePlayer = new RoutinePlayer(this);
        return routinePlayer;
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
        PlayerEntity player = editorHandler.getPlayer();
        if (player == null) return;
        Vec3d pos = player.getPos();
        int tick = timeline.getCurrentEditingTick();
        addParticlePoint(tick, new ParticlePoint(editorHandler.getSelectedParticle(), pos.x, pos.y, pos.z, tick));
    }

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        if (routinePlayer != null && routinePlayer.isPlaying()) routinePlayer.stop();
        timeline.addParticlePoint(time, particlePoint);
    }

    public void newPath(Path path){
        editingPath = path;
    }

    public void clearRoutine(){
        editingPath = null;
    }

    public Optional<Path> getEditingPath(){
        return Optional.ofNullable(editingPath);
    }

    public Optional<ParticlePoint> tryToSelectPoint(PlayerEntity player) {
        if (timeline.isEmpty()) return Optional.empty();
        return new SelectionCalculator(
                timeline.getPoints().subList(timeline.onionLowerBound(), timeline.onionUpperBound() + 1), player)
                .getSelectedPoint();
    }

    public static Optional<byte[]> serialize(Routine routine){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(routine);
            return Optional.of(bos.toByteArray());
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
