package net.pulga22.particlestudio.core.routines.managers;

import net.minecraft.world.World;
import net.pulga22.particlestudio.core.routines.RoutinePlayer;

import java.util.HashSet;
import java.util.Set;

public class ParticleClientTicker {

    public final Set<RoutinePlayer> players = new HashSet<>();

    private ParticleClientTicker(){}

    private static class SingletonHolder {
        private static final ParticleClientTicker INSTANCE = new ParticleClientTicker();
    }

    public static ParticleClientTicker getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void subscribe(RoutinePlayer routinePlayer){
        players.add(routinePlayer);
    }

    public void unsubscribe(RoutinePlayer routine){
        players.remove(routine);
    }

    public void tick(World world){
        players.forEach(routinePlayer -> routinePlayer.tick(world));
    }

}
