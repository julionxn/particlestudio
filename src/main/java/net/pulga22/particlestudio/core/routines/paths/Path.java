package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.PointRenderer;
import net.pulga22.particlestudio.core.routines.Routine;

public abstract class Path {

    protected final Vec3d from;
    protected final Vec3d to;
    private float density = 1;
    private final int duration;
    protected final int startingTick;
    protected final int finishingTick;

    public Path(ParticlePoint from, ParticlePoint to){
        this.from = from.getPosition();
        this.to = to.getPosition();
        duration = to.tick - from.tick;
        startingTick = from.tick;
        finishingTick = to.tick;
    }

    public void render(WorldRenderContext context) {
        PointRenderer.renderPathPoint(context, fromPos());
        PointRenderer.renderPathPoint(context, toPos());
    };
    public abstract void transform(Routine routine, String selectedParticle);

    public Vec3d fromPos(){
        return from;
    }

    public Vec3d toPos(){
        return to;
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
