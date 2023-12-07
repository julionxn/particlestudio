package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class SelectionCalculator {

    private final Vec3d currentLocation;
    private final double standardYaw;
    private final double standardPitch;
    private final double angleThreshold = Math.toRadians(4.2);
    private double leastDistance = Double.POSITIVE_INFINITY;
    private ParticlePoint selectedPoint;
    private static final double PI_HALF = 1.570796;

    public SelectionCalculator(List<List<ParticlePoint>> points, PlayerEntity player){
        this.standardPitch = clampAngle(Math.toRadians(player.getPitch()) + PI_HALF);
        this.standardYaw = clampAngle(Math.toRadians(player.getHeadYaw()) + PI_HALF);
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
                Vec3d particlePosition = particlePoint.getPosition();
                Vec3d distance = particlePosition.subtract(currentLocation);
                double yaw = Math.atan2(distance.z, distance.x);
                double pitch = Math.atan2(-distance.y, Math.sqrt((distance.x * distance.x) + (distance.z * distance.z))) + PI_HALF;
                areAnglesClose(yaw, pitch, (yawDif, pitchDif) -> {
                    double distanceTo = distance.x * distance.x + distance.y * distance.y + distance.z * distance.z;
                    if (distanceTo < leastDistance){
                        selectedPoint = particlePoint;
                        leastDistance = distanceTo;
                    }
                });
            }
        }
    }

    public Optional<ParticlePoint> getSelectedPoint(){
        if (selectedPoint == null) return Optional.empty();
        return Optional.of(selectedPoint);
    }

    private void areAnglesClose(double calculatedYaw, double calculatedPitch, BiConsumer<Double, Double> ifSo) {
        double yawClose = Math.abs(standardYaw - calculatedYaw);
        double pitchClose = Math.abs(standardPitch - calculatedPitch);
        if (yawClose < angleThreshold && pitchClose < angleThreshold) ifSo.accept(yawClose, pitchClose);
    }

}
