package net.pulga22.particlestudio.core.routines;

import net.minecraft.world.World;
import net.pulga22.particlestudio.core.routines.managers.ParticleClientTicker;

import java.util.List;

public class RoutinePlayer {

    private final Routine routine;
    private boolean playing;
    private int currentTick;
    private List<List<ParticlePoint>> points;

    public RoutinePlayer(Routine routine){
        this.routine = routine;
    }

    public void play(){
        if (playing) return;
        List<List<ParticlePoint>> points = routine.getTimeline().getPoints();
        if (points.isEmpty()) return;
        this.points = points;
        ParticleClientTicker.getInstance().subscribe(this);
        playing = true;
    }

    public void pause(){
        if (!playing) return;
        ParticleClientTicker.getInstance().unsubscribe(this);
        playing = false;
    }

    public void stop(){
        pause();
        currentTick = 0;
    }

    public void restart(){
        if (!playing) {
            play();
            return;
        }
        currentTick = 0;
    }

    public void tick(World world){
        if (points == null) return;
        List<ParticlePoint> tickPoints = points.get(currentTick);
        tickPoints.forEach(particlePoint -> particlePoint.spawnParticle(world));
        if (currentTick + 1 >= points.size()) currentTick = -1;
        currentTick++;
    }

    public boolean isPlaying(){
        return playing;
    }

}
