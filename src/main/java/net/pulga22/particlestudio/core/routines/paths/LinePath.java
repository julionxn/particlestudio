package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.Routine;

public class LinePath extends Path {

    public LinePath(ParticlePoint from, ParticlePoint to) {
        super(from, to);
    }

    @Override
    public void render(WorldRenderContext context) {
        super.render(context);
    }

    @Override
    public void transform(Routine routine) {

    }
}
