package net.pulga22.particlestudio.core.routines;

import net.minecraft.world.World;

import java.util.List;

public class RoutinePlayer {

    private final List<List<ParticlePoint>> particlePoints;
    private boolean playing;
    private int currentTick;

    public RoutinePlayer(List<List<ParticlePoint>> particlePoints){
        this.particlePoints = particlePoints;
    }

    public void play(){
        if (particlePoints.isEmpty()) return;
        ParticleClientTicker.getInstance().subscribe(this);
        playing = true;
    }

    public void pause(){
        ParticleClientTicker.getInstance().unsubscribe(this);
        playing = false;
    }

    public void setCurrentTick(int tick){
        currentTick = tick;
    }

    public void tick(World world){
        List<ParticlePoint> points = particlePoints.get(currentTick);
        points.forEach(particlePoint -> particlePoint.spawnParticle(world));
        if (currentTick + 1 >= particlePoints.size()) currentTick = -1;
        currentTick++;
    }

    public int length(){
        return particlePoints.size();
    }

    public boolean isPlaying(){
        return playing;
    }

}
