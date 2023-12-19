package net.pulga22.particlestudio.core.routines;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.pulga22.particlestudio.core.editor.handlers.SelectionHandler;

import java.util.List;

public class RoutineRenderer {

    private final Routine routine;
    private final Timeline timeline;
    private final RoutinePlayer routinePlayer;
    private final SelectionHandler selectionHandler;

    public RoutineRenderer(Routine routine){
        this.routine = routine;
        this.timeline = routine.getTimeline();
        this.routinePlayer = routine.getRoutinePlayer();
        this.selectionHandler = routine.getSelectionHandler();
    }

    public void render(WorldRenderContext context){
        List<ParticlePoint> selectedPoints = selectionHandler.get();
        if (timeline.isEmpty() || (routinePlayer != null && routinePlayer.isPlaying())) return;
        renderPoints(context, selectedPoints);
        renderPaths(context);
    }

    private void renderPaths(WorldRenderContext context){
        routine.getEditingPath().ifPresent(path -> path.render(context));
    }

    private void renderPoints(WorldRenderContext context, List<ParticlePoint> selectedPoints){
        int selectedTick = timeline.getCurrentEditingTick();
        int lower = timeline.onionLowerBound();
        int upper = timeline.onionUpperBound();
        double sigma = Math.min(selectedTick - lower, upper - selectedTick) / 2.0;
        double sigmaSquared = sigma * sigma;
        for (int tick = lower; tick <= upper; tick++) {
            List<ParticlePoint> points = timeline.getPointsOfTick(tick);
            points.forEach(particlePoint -> {
                if (selectedPoints.contains(particlePoint)){
                    PointRenderer.renderSelectedPoint(context, particlePoint.getPosition());
                    return;
                }
                float gradient = (float) generateGradient(particlePoint.tick, selectedTick, sigmaSquared);
                PointRenderer.renderParticlePoint(context, particlePoint.getPosition(), gradient);
            });
        }
    }

    private double generateGradient(int tick, int editing, double sigmaSquared){
        double exponent = -Math.pow((tick - editing), 2) / (2 * sigmaSquared);
        return 1 - Math.pow(Math.E, exponent);
    }

}
