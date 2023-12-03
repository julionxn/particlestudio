package net.pulga22.particlestudio.core.routines.managers;

import net.pulga22.particlestudio.core.routines.Routine;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Optional;

public class WorldRoutines implements Serializable {

    public final HashMap<String, Routine> routines = new HashMap<>(){{
        put("hola", new Routine());
        put("adios", new Routine());
    }};

    public Optional<Routine> getRoutine(String name){
        if (routines.containsKey(name)) return Optional.of(routines.get(name));
        return Optional.empty();
    }

    public void addRoutine(String name, Routine routine){
        routines.put(name, routine);
    }

}
