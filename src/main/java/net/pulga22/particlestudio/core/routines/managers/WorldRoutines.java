package net.pulga22.particlestudio.core.routines.managers;

import net.pulga22.particlestudio.core.routines.Routine;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class WorldRoutines implements Serializable {

    public final HashMap<String, Routine> routines = new HashMap<>();

    public Optional<Routine> getRoutine(String name) {
        return Optional.ofNullable(routines.get(name));
    }

    public Collection<Routine> getRoutines(){
        return routines.values();
    }

    public void setRoutine(String name, Routine routine){
        routines.put(name, routine);
    }

}
