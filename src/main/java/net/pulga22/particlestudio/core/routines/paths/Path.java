package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.List;

public abstract class Path {

    protected final Vec3d from;
    protected final Vec3d to;
    private float density = 1;
    private final int duration;
    protected final int startingTick;
    protected final int finishingTick;
    private final List<GhostPoint> ghostPoints = new ArrayList<>();

    public Path(ParticlePoint from, ParticlePoint to){
        this.from = from.getPosition();
        this.to = to.getPosition();
        duration = Math.abs(to.tick - from.tick);
        startingTick = Math.min(from.tick, to.tick);
        finishingTick = Math.max(from.tick, to.tick);
    }

    public void render(WorldRenderContext context) {
        ghostPoints.forEach(ghostPoint -> ghostPoint.render(context));
        renderPath(context);
    }

    public abstract void renderPath(WorldRenderContext context);

    public void apply(Routine routine, String selectedParticle){
        ghostPoints.forEach(ghostPoint -> {
            Vec3d pos = ghostPoint.pos();
            routine.addParticlePoint(ghostPoint.tick(), new ParticlePoint(
                    selectedParticle, pos.x, pos.y, pos.z, ghostPoint.tick()
            ));
        });
    }

    public abstract void calculate(List<GhostPoint> points);

    public Vec3d fromPos(){
        return from;
    }

    public Vec3d toPos(){
        return to;
    }

    public void changeDensity(float to){
        density = to;
        recalculate();
    }

    protected void recalculate(){
        ghostPoints.clear();
        calculate(ghostPoints);
    }

    public float getDensity(){
        return density;
    }

    public int getDuration(){
        return duration;
    }

    public List<GhostPoint> getGhostPoints(){
        return ghostPoints;
    }

}
