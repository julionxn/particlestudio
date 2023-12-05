package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.PointRenderer;
import net.pulga22.particlestudio.core.routines.Routine;

public class LinePath extends Path {

    public LinePath(ParticlePoint from, ParticlePoint to) {
        super(from, to);
    }

    @Override
    public void render(WorldRenderContext context) {
        super.render(context);
        PointRenderer.renderLine(context, fromPos(), toPos());
    }

    @Override
    public void transform(Routine routine, String selectedParticle) {
        Vec3d toPos = toPos();
        Vec3d fromPos = fromPos();
        final float density = getDensity();
        final int duration = getDuration();
        final double difX = (toPos.x - fromPos.x) / (density + 1);
        final double difY = (toPos.y - fromPos.y) / (density + 1);
        final double difZ = (toPos.z - fromPos.z) / (density + 1);
        final int points = (int) Math.max(1, Math.floor(density));
        final int iters = Math.min(duration, points);
        final int deltaTick = (int) Math.ceil((double) duration / (points + 1));
        final int pointsPerTick = points / iters;
        int remainingPoints = points - (iters * pointsPerTick);
        int currentPoint = 1;
        for (int i = 0; i < iters; i++) {
            int currentTick = ((i + 1) * deltaTick) + startingTick;
            for (int j = 0; j < pointsPerTick; j++) {
                routine.addParticlePoint(currentTick, new ParticlePoint(selectedParticle,
                        (currentPoint * difX) + fromPos.x,
                        (currentPoint * difY) + fromPos.y,
                        (currentPoint * difZ) + fromPos.z, currentTick));
                currentPoint++;
            }
            if (remainingPoints > 0){
                routine.addParticlePoint(currentTick, new ParticlePoint(selectedParticle,
                        (currentPoint * difX) + fromPos.x,
                        (currentPoint * difY) + fromPos.y,
                        (currentPoint * difZ) + fromPos.z, currentTick));
                currentPoint++;
                remainingPoints--;
            }
        }
    }
}
