package net.pulga22.particlestudio.core.routines.managers;

import net.pulga22.particlestudio.core.routines.Routine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

public class PartialRoutine {

    public final UUID uuid;
    private final List<byte[]> loadedBytes = new ArrayList<>();

    public PartialRoutine(UUID uuid){
        this.uuid = uuid;
    }

    public void appendBytes(int index, byte [] data){
        int currentSize = loadedBytes.size();
        while (currentSize < index) {
            loadedBytes.add(null);
            currentSize++;
        }
        loadedBytes.add(index, data);
    }

    public int getSize(){
        return (int) loadedBytes.stream().filter(Objects::nonNull).count();
    }

    public Optional<Routine> getRoutine(){
        Optional<byte[]> optionalBytes = concatenateByteArrays(loadedBytes);
        if (optionalBytes.isEmpty()) return Optional.empty();
        byte[] bytes = optionalBytes.get();
        return Routine.deserialize(bytes);
    }

    private Optional<byte[]> concatenateByteArrays(List<byte[]> listOfByteArrays) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            for (byte[] byteArray : listOfByteArrays) {
                outputStream.write(byteArray);
            }
            return Optional.of(outputStream.toByteArray());
        } catch (IOException e) {
            return Optional.empty();
        }
    }

}
