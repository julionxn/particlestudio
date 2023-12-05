package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SelectionCalculator {

    private final List<ParticlePoint> usefulPoints = new ArrayList<>();
    private final Vec3d currentLocation;
    private final double standardYaw;
    private final double standardPitch;
    private final double angleThreshold = Math.toRadians(5);
    private ParticlePoint selectedPoint;

    public SelectionCalculator(List<List<ParticlePoint>> points, PlayerEntity player){
        this.standardPitch = clampAngle(Math.toRadians(player.getPitch() + 90));
        this.standardYaw = clampAngle(Math.toRadians(player.getHeadYaw() + 90));
        this.currentLocation = player.getPos().add(0, 1.5, 0);
        cleanPoints(points);
    }

    private double clampAngle(double angle) {
        angle = angle % (2 * Math.PI);
        if (angle > Math.PI) {
            angle -= 2 * Math.PI;
        } else if (angle < -Math.PI) {
            angle += 2 * Math.PI;
        }
        return angle;
    }

    private void cleanPoints(List<List<ParticlePoint>> points){
        for (List<ParticlePoint> pointsOf : points) {
            for (ParticlePoint particlePoint : pointsOf) {
                Vec3d distance = particlePoint.getPosition().subtract(currentLocation);
                double yaw = Math.atan2(distance.z, distance.x);
                double pitch = Math.atan2(-distance.y, Math.sqrt((distance.x * distance.x) + (distance.z * distance.z))) + Math.toRadians(90);
                if (areAnglesClose(standardYaw, standardPitch, yaw, pitch)){
                    usefulPoints.add(particlePoint);
                }
            }
        }
        double distance = Double.POSITIVE_INFINITY;
        for (ParticlePoint usefulPoint : usefulPoints) {
            double distanceTo = currentLocation.distanceTo(usefulPoint.getPosition());
            if (distanceTo < distance){
                selectedPoint = usefulPoint;
                distance = distanceTo;
            }
        }
    }

    public Optional<ParticlePoint> getSelectedPoint(){
        if (selectedPoint == null) return Optional.empty();
        return Optional.of(selectedPoint);
    }

    private boolean areAnglesClose(double normalYaw, double normalPitch, double calculatedYaw, double calculatedPitch) {
        boolean yawClose = Math.abs(normalYaw - calculatedYaw) < angleThreshold;
        boolean pitchClose = Math.abs(normalPitch - calculatedPitch) < angleThreshold;
        return yawClose && pitchClose;
    }

}
