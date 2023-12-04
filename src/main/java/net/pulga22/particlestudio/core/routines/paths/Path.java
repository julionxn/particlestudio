package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.Routine;
import org.jetbrains.annotations.Nullable;

public abstract class Path {

    protected final Vec3d from;
    @Nullable
    protected Vec3d to;
    private int density = 1;
    private int duration = 1;

    public Path(Vec3d from){
        this.from = from;
    }

    public abstract void render(WorldRenderContext context);
    public abstract void transform(Routine routine);

    public Vec3d from(){
        return from;
    }

    public void setTo(Vec3d to){
        this.to = to;
    }

    public Vec3d to(){
        return from;
    }

    public void changeDensity(int to){
        density = to;
    }

    public int getDensity(){
        return density;
    }

    public void changeDuration(int to){
        duration = to;
    }

    public int getDuration(){
        return duration;
    }

}
