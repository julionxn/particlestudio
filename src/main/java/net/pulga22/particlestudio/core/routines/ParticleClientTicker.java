package net.pulga22.particlestudio.core.routines;

import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class ParticleClientTicker {

    private static ParticleClientTicker instance;
    public final Set<RoutinePlayer> players = new HashSet<>();

    public static ParticleClientTicker getInstance(){
        if (instance == null) instance = new ParticleClientTicker();
        return instance;
    }

    public void subscribe(RoutinePlayer routine){
        players.add(routine);
    }

    public void unsubscribe(RoutinePlayer routine){
        players.remove(routine);
    }

    public void tick(World world){
        players.forEach(routinePlayer -> routinePlayer.tick(world));
    }

}
