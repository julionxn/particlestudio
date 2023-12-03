package net.pulga22.particlestudio.core.routines;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.EditorHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Routine implements Serializable {

    private final List<List<ParticlePoint>> timeline = new ArrayList<>();
    private int displayLength;
    private int actualLength;
    private transient int currentEditingTick;
    private transient int onionLowerBound;
    private transient int onionUpperBound;
    private transient RoutinePlayer routinePlayer;

    public void adjustFrame(int in){
        if (in == 0) return;
        if (in < 0) { decreaseFrame(in); return; }
        increaseFrame(in);
    }

    private void decreaseFrame(int in){
        if (currentEditingTick - in < 0 || displayLength - in < actualLength) return;
        displayLength -= in;
        currentEditingTick -= in;
    }

    private void increaseFrame(int in){
        displayLength += in;
        currentEditingTick += in;
    }

    public void adjustOnionUpperBound(int in){
        if (in == 0) return;
        int newBound = onionUpperBound + in;
        if (newBound > actualLength || newBound <= onionLowerBound) return;
        onionUpperBound += in;
    }

    public void adjustOnionLowerBound(int in){
        if (in == 0) return;
        int newBound = onionLowerBound + in;
        if (newBound < 0 || newBound >= onionUpperBound) return;
        onionLowerBound += in;
    }

    public int getCurrentEditingTick(){
        return currentEditingTick;
    }

    public int displayLength(){
        return displayLength;
    }

    public int length() {
        return actualLength;
    }

    public int onionLowerBound(){
        return onionLowerBound;
    }

    public int onionUpperBound(){
        return onionUpperBound;
    }

    public void renderPreview(WorldRenderContext context){
        if (timeline.isEmpty() || isPlaying()) return;
        for (int i = onionLowerBound; i <= onionUpperBound; i++) {
            List<ParticlePoint> points = timeline.get(i);
            points.forEach(particlePoint -> {
                particlePoint.renderPreview(context);
            });
        }
    }

    public void play(){
        if (routinePlayer == null || routinePlayer.length() != timeline.size()) {
            routinePlayer = new RoutinePlayer(timeline);
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
        addParticlePoint(currentEditingTick, new ParticlePoint(editorHandler.getSelectedParticle(), pos.x, pos.y, pos.z));
    }

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        if (routinePlayer != null && routinePlayer.isPlaying()) stop();
        while (timeline.size() <= time) {
            timeline.add(new ArrayList<>());
        }
        List<ParticlePoint> particleList = timeline.get(time);
        particleList.add(particlePoint);
        if (time > actualLength) {
            actualLength = time;
            displayLength = time;
        }
        if (time == displayLength){
            adjustFrame(1);
            adjustOnionUpperBound(1);
        }
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
