package net.pulga22.particlestudio.core.routines;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.HashMap;

public class WorldRoutines implements Serializable {

    public final HashMap<String, Routine> routines = new HashMap<>();

    @Nullable
    public Routine getRoutine(String name){
        return routines.get(name);
    }

    public void addRoutine(String name, Routine routine){
        routines.put(name, routine);
    }

}
