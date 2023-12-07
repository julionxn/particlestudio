package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.PointRenderer;

import java.util.List;

public class LinePath extends Path {

    public LinePath(ParticlePoint from, ParticlePoint to) {
        super(from, to);
        recalculate();
    }

    @Override
    public void renderPath(WorldRenderContext context) {
        PointRenderer.renderLine(context, fromPos(), toPos());
    }

    @Override
    public void calculate(List<GhostPoint> points) {
        Vec3d toPos = toPos();
        Vec3d fromPos = fromPos();
        final float density = getDensity();
        final int duration = getDuration();
        final Vec3d deltaPosition = new Vec3d((toPos.x - fromPos.x) / (density + 1),
                (toPos.y - fromPos.y) / (density + 1),
                (toPos.z - fromPos.z) / (density + 1));
        final int maxPoints = (int) Math.max(1, Math.floor(density));
        final int items = Math.min(duration, maxPoints);
        final int deltaTick = (int) Math.ceil((double) duration / (maxPoints + 1));
        final int pointsPerTick = maxPoints / items;
        int remainingPoints = maxPoints - (items * pointsPerTick);
        int currentPoint = 1;
        for (int i = 0; i < items; i++) {
            int currentTick = ((i + 1) * deltaTick) + startingTick;
            for (int j = 0; j < pointsPerTick; j++) {
                points.add(getPoint(currentTick, currentPoint++, fromPos, deltaPosition));
            }
            if (remainingPoints > 0){
                points.add(getPoint(currentTick, currentPoint++, fromPos, deltaPosition));
                remainingPoints--;
            }
        }
    }

    private GhostPoint getPoint(int currentTick, int currentPoint, Vec3d fromPos, Vec3d deltaPosition){
        return new GhostPoint(currentTick, new Vec3d(
                (currentPoint * deltaPosition.x) + fromPos.x,
                (currentPoint * deltaPosition.y) + fromPos.y,
                (currentPoint * deltaPosition.z) + fromPos.z));
    }
}
