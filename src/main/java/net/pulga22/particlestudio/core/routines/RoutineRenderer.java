package net.pulga22.particlestudio.core.routines;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.handlers.SelectionHandler;

import java.util.List;

public class RoutineRenderer {

    private static final Identifier PARTICLE_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "points/point.png");
    private static final Identifier SELECTED_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "points/selected.png");
    private final Routine routine;
    private final Timeline timeline;
    private final RoutinePlayer routinePlayer;
    private final SelectionHandler selectionHandler;

    public RoutineRenderer(Routine routine) {
        this.routine = routine;
        this.timeline = routine.getTimeline();
        this.routinePlayer = routine.getRoutinePlayer();
        this.selectionHandler = routine.getSelectionHandler();
    }

    public void render(WorldRenderContext context) {
        List<ParticlePoint> selectedPoints = selectionHandler.get();
        if (timeline.isEmpty() || (routinePlayer != null && routinePlayer.isPlaying())) return;
        renderPoints(context, selectedPoints);
        renderPaths(context);
    }

    private void renderPaths(WorldRenderContext context) {
        routine.getEditingPath().ifPresent(path -> path.render(context));
    }

    private void renderPoints(WorldRenderContext context, List<ParticlePoint> selectedPoints) {
        int selectedTick = timeline.getCurrentEditingTick();
        int lower = timeline.onionLowerBound();
        int upper = timeline.onionUpperBound();
        double sigma = Math.min(selectedTick - lower, upper - selectedTick) / 2.0;
        double sigmaSquared = sigma * sigma;
        for (int tick = lower; tick <= upper; tick++) {
            List<ParticlePoint> points = timeline.getPointsOfTick(tick);
            points.forEach(particlePoint -> {
                if (selectedPoints.contains(particlePoint)) {
                    PointRenderer.renderBillboardTexture(context, particlePoint.getPosition(), SELECTED_POINT_TEXTURE);
                    return;
                }
                PointRenderer.renderBillboardTexture(context, particlePoint.getPosition(), PARTICLE_POINT_TEXTURE);
            });
        }
    }

}
