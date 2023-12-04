package net.pulga22.particlestudio.core.routines;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.SelectionCalculator;
import net.pulga22.particlestudio.core.routines.paths.Path;

import java.io.*;
import java.util.List;
import java.util.Optional;

public class Routine implements Serializable {

    private final Timeline timeline = new Timeline();
    private transient RoutinePlayer routinePlayer;
    private transient Path editingPath;

    public Timeline getTimeline(){
        return timeline;
    }

    public void render(WorldRenderContext context, List<ParticlePoint> selectedPoints){
        if (timeline.isEmpty() || isPlaying()) return;
        renderPoints(context, selectedPoints);
        renderPaths(context);
    }

    private void renderPaths(WorldRenderContext context){
        if (editingPath == null) return;
        editingPath.render(context);
    }

    private void renderPoints(WorldRenderContext context, List<ParticlePoint> selectedPoints){
        for (int i = timeline.onionLowerBound(); i <= timeline.onionUpperBound(); i++) {
            List<ParticlePoint> points = timeline.getPointsOfTick(i);
            points.forEach(particlePoint -> {
                if (selectedPoints.contains(particlePoint)){
                    PointRenderer.renderSelectedPoint(context, particlePoint.getPosition());
                    return;
                }
                PointRenderer.renderParticlePoint(context, particlePoint.getPosition());
            });
        }
    }

    public void play(){
        if (routinePlayer == null || routinePlayer.length() != timeline.size()) {
            routinePlayer = new RoutinePlayer(timeline.getPoints());
        }
        if (routinePlayer.isPlaying()) return;
        routinePlayer.play();
    }

    public void pause(){
        if (routinePlayer == null) return;
        routinePlayer.pause();
    }

    public void stop(){
        if (routinePlayer == null) return;
        routinePlayer.setCurrentTick(0);
        routinePlayer.pause();
    }

    public void restart(){
        if (routinePlayer == null || !routinePlayer.isPlaying()) {
            play();
            return;
        }
        routinePlayer.setCurrentTick(0);
    }

    public boolean isPlaying(){
        if (routinePlayer == null) return false;
        return routinePlayer.isPlaying();
    }

    public void addParticlePoint(EditorHandler editorHandler){
        PlayerEntity player = editorHandler.getPlayer();
        if (player == null) return;
        Vec3d pos = player.getPos();
        int tick = timeline.getCurrentEditingTick();
        addParticlePoint(tick, new ParticlePoint(editorHandler.getSelectedParticle(), pos.x, pos.y, pos.z, tick));
    }

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        if (routinePlayer != null && routinePlayer.isPlaying()) stop();
        timeline.addParticlePoint(time, particlePoint);
    }

    public void newPath(Path path){
        editingPath = path;
    }

    public Path getEditingPath(){
        return editingPath;
    }

    public Optional<ParticlePoint> tryToSelectPoint(PlayerEntity player) {
        if (timeline.isEmpty()) return Optional.empty();
        Vec3d pos = player.getPos().add(0, 1.5, 0);
        double pitch = clampAngle(Math.toRadians(player.getPitch() + 90));
        double yaw = clampAngle(Math.toRadians(player.getHeadYaw() + 90));
        return new SelectionCalculator(timeline.getPoints()
                .subList(timeline.onionLowerBound(),
                timeline.onionUpperBound() + 1),
                pos, yaw, pitch).getSelectedPoint();
    }

    private double clampAngle(double angle) {
        angle = angle % (2 * Math.PI);
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
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
}
