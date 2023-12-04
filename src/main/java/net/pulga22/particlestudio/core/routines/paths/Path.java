package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.PointRenderer;
import net.pulga22.particlestudio.core.routines.Routine;

public abstract class Path {

    protected final ParticlePoint from;
    protected final ParticlePoint to;
    private int density = 1;
    private final int duration;

    public Path(ParticlePoint from, ParticlePoint to){
        this.from = from;
        this.to = to;
        duration = to.tick - from.tick;
    }

    public void render(WorldRenderContext context) {
        PointRenderer.renderPathPoint(context, from());
        PointRenderer.renderPathPoint(context, to());
    };
    public abstract void transform(Routine routine);

    public Vec3d from(){
        return from.getPosition();
    }

    public Vec3d to(){
        return from.getPosition();
    }

    public void changeDensity(int to){
        density = to;
    }

    public int getDensity(){
        return density;
    }

    public int getDuration(){
        return duration;
    }

}
