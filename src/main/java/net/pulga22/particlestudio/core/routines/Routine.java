package net.pulga22.particlestudio.core.routines;

import java.io.*;

public class Routine extends RoutineLike implements Serializable {

    private int currentEditingTick = 0;

    public void insertRoutine(int startingTime, RoutineLike routineLike){
        routineLike.getTimeline().forEach((time, points) -> {
            int timeToInsert = startingTime + time;
            points.forEach(point -> addParticlePoint(timeToInsert, point));
        });
    }

    public void calculateLength(){
        int max = -1;
        for (Integer i : getTimeline().keySet()) {
            if (i > max) max = i;
        }
        length = max;
    }

    public int getCurrentEditingTick(){
        return currentEditingTick;
    }

    public int length(){
        return length;
    }

    public static byte[] serialize(Routine routine){
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(routine);
            return bos.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    public static Routine deserialize(byte[] serializedRoutine){
        try (ByteArrayInputStream bis = new ByteArrayInputStream(serializedRoutine);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Routine) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

}
