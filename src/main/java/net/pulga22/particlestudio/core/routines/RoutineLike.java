package net.pulga22.particlestudio.core.routines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RoutineLike {

    protected int length = 0;
    private final HashMap<Integer, List<ParticlePoint>> timeline = new HashMap<>();

    public void addParticlePoint(int time, ParticlePoint particlePoint){
        timeline.computeIfAbsent(time, k -> new ArrayList<>());
        timeline.get(time).add(particlePoint);
    }

    public HashMap<Integer, List<ParticlePoint>> getTimeline(){
        return timeline;
    }

}
