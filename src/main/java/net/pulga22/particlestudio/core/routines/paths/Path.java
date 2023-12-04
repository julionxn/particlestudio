package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.PointRenderer;
import net.pulga22.particlestudio.core.routines.Routine;

public abstract class Path {

    protected final ParticlePoint from;
    protected final ParticlePoint to;
    private float density = 1;
    private final int duration;
    protected final int startingTick;
    protected final int finishingTick;

    public Path(ParticlePoint from, ParticlePoint to){
        this.from = from;
        this.to = to;
        duration = to.tick - from.tick;
        startingTick = from.tick;
        finishingTick = to.tick;
    }

    public void render(WorldRenderContext context) {
        PointRenderer.renderPathPoint(context, from());
        PointRenderer.renderPathPoint(context, to());
    };
    public abstract void transform(Routine routine, String selectedParticle);

    public Vec3d from(){
        return from.getPosition();
    }

    public Vec3d to(){
        return to.getPosition();
    }

    public void changeDensity(float to){
        density = to;
    }

    public float getDensity(){
        return density;
    }

    public int getDuration(){
        return duration;
    }

}
