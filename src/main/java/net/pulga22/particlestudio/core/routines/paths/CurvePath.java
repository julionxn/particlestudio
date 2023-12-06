package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.PointRenderer;

import java.util.List;

public class CurvePath extends Path {

    private static final Identifier CONTROL_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "points/control.png");
    private Vec3d firstControlPoint;
    private Vec3d secondControlPoint;

    public CurvePath(ParticlePoint from, ParticlePoint to) {
        super(from, to);
        firstControlPoint = from.getPosition().add(0, 1, 0);
        secondControlPoint = to.getPosition().add(0, 1, 0);
        recalculate();
    }

    @Override
    public void render(WorldRenderContext context) {
        super.render(context);
        PointRenderer.renderBillboardTexture(context, firstControlPoint, CONTROL_POINT_TEXTURE);
        PointRenderer.renderBillboardTexture(context, secondControlPoint, CONTROL_POINT_TEXTURE);
    }

    @Override
    public void renderPath(WorldRenderContext context) {
        Vec3d firstPos = from;
        for (GhostPoint ghostPoint : getGhostPoints()) {
            PointRenderer.renderLine(context, firstPos, ghostPoint.pos());
            firstPos = ghostPoint.pos();
        }
        PointRenderer.renderLine(context, firstPos, to);
    }

    @Override
    public void calculate(List<GhostPoint> points) {
        final float density = getDensity();
        final int duration = getDuration();
        final int maxPoints = (int) Math.max(1, Math.floor(density));
        final int iters = Math.min(duration, maxPoints);
        final int deltaTick = (int) Math.ceil((double) duration / (maxPoints + 1));
        final int pointsPerTick = maxPoints / iters;
        int remainingPoints = maxPoints - (iters * pointsPerTick);
        float deltaT = 1f / (maxPoints + 1);
        int currentPoint = 0;
        for (int i = 0; i < iters; i++) {
            int currentTick = ((i + 1) * deltaTick) + startingTick;
            for (int j = 0; j < pointsPerTick; j++) {
                points.add(getPoint(currentTick, calculatePos(deltaT * (currentPoint + 1))));
                currentPoint++;
            }
            if (remainingPoints > 0){
                points.add(getPoint(currentTick, calculatePos(deltaT * (currentPoint + 1))));
                currentPoint++;
                remainingPoints--;
            }
        }
    }

    private Vec3d calculatePos(float t){
        double u = 1 - t;
        double x = u*u*u * from.x + 3 * u*u * t * firstControlPoint.x + 3 * u * t*t * secondControlPoint.x + t*t*t * to.x;
        double y = u*u*u * from.y + 3 * u*u * t * firstControlPoint.y + 3 * u * t*t * secondControlPoint.y + t*t*t * to.y;
        double z = u*u*u * from.z + 3 * u*u * t * firstControlPoint.z + 3 * u * t*t * secondControlPoint.z + t*t*t * to.z;
        return new Vec3d(x, y, z);
    }

    private GhostPoint getPoint(int currentTick, Vec3d pos){
        return new GhostPoint(currentTick, new Vec3d(pos.x, pos.y, pos.z));
    }

    public void setFirstControlPoint(Vec3d position){
        firstControlPoint = position;
        recalculate();
    }

    public void setSecondControlPoint(Vec3d position){
        secondControlPoint = position;
        recalculate();
    }


}
