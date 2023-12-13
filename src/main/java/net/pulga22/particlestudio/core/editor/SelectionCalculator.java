package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.routines.ParticlePoint;

import java.util.List;
import java.util.Optional;

public class SelectionCalculator {

    private final Vec3d currentLocation;
    private double leastDistance = Double.POSITIVE_INFINITY;
    private ParticlePoint selectedPoint;
    private final Vec3d playerLookingVector;
    private static final double TWO_PI = 6.283185;

    public SelectionCalculator(List<List<ParticlePoint>> points, PlayerEntity player){
        double standardPitch = clampAngle(Math.toRadians(player.getPitch()));
        double standardYaw = clampAngle(Math.toRadians(player.getYaw()));
        this.currentLocation = player.getPos().add(0, 1.5, 0);
        this.playerLookingVector = new Vec3d(Math.sin(standardYaw) * Math.cos(standardPitch),
                Math.sin(standardPitch), -Math.cos(standardYaw) * Math.cos(standardPitch))
                .normalize();
        pickPoint(points);
    }

    private double clampAngle(double angle) {
        angle = angle % (TWO_PI);
        if (angle > Math.PI) {
            angle -= TWO_PI;
        } else if (angle < -Math.PI) {
            angle += TWO_PI;
        }
        return angle;
    }

    private void pickPoint(List<List<ParticlePoint>> points){
        points.forEach(pointOfTick -> {
            pointOfTick.forEach(particlePoint -> {
                Vec3d playerToPointVector = currentLocation.subtract(particlePoint.getPosition());
                double distance = playerToPointVector.length();
                playerToPointVector = playerToPointVector.normalize();
                double lookness = playerToPointVector.dotProduct(playerLookingVector);
                if (lookness > 0.997 && distance < leastDistance){
                    selectedPoint = particlePoint;
                    leastDistance = distance;
                }
            });
        });
    }

    public Optional<ParticlePoint> getSelectedPoint(){
        if (selectedPoint == null) return Optional.empty();
        return Optional.of(selectedPoint);
    }

}
