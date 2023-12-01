package net.pulga22.particlestudio.core.routines;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.EditorHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class Routine implements Serializable {

    private int displayLength;
    private int actualLength;
    private transient int currentEditingTick;
    private final HashMap<Integer, List<ParticlePoint>> timeline = new HashMap<>();
    private transient int onionLowerBound;
    private transient int onionUpperBound;
    private String selectedParticle = "minecraft:end_rod";

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

    public int length(){
        return displayLength;
    }

    public int onionLowerBound(){
        return onionLowerBound;
    }

    public int onionUpperBound(){
        return onionUpperBound;
    }

    public void addParticlePoint(EditorHandler editorHandler){
        PlayerEntity player = editorHandler.getPlayer();
        if (player == null) return;
        Vec3d pos = player.getPos();
        addParticlePoint(currentEditingTick, new ParticlePoint(selectedParticle, pos.x, pos.y, pos.z));
    }

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        timeline.computeIfAbsent(time, k -> new ArrayList<>());
        timeline.get(time).add(particlePoint);
        if (time > actualLength) {
            actualLength = time;
            displayLength = time;
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
