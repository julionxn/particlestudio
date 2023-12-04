package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.PointRenderer;
import net.pulga22.particlestudio.core.routines.Routine;

public class LinePath extends Path{

    public LinePath(Vec3d from) {
        super(from);
    }

    @Override
    public void render(WorldRenderContext context) {
        PointRenderer.renderPathPoint(context, from);
        if (to != null) PointRenderer.renderPathPoint(context, to);
    }

    @Override
    public void transform(Routine routine) {

    }
}
