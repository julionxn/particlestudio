package net.pulga22.particlestudio.core.routines;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.pulga22.particlestudio.ParticleStudio;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import java.io.Serializable;
import java.util.Optional;

public class ParticlePoint implements Serializable {

    public final double[] position = {0, 0, 0};
    public final String particleType;

    public ParticlePoint(String particleType, double x, double y, double z){
        position[0] = x;
        position[1] = y;
        position[2] = z;
        this.particleType = particleType;
    }

    public void spawnParticle(World world){
        Optional<ParticleType<?>> particleTypeOptional = ParticleRoutinesManager.getInstance().getParticleType(particleType);
        particleTypeOptional.ifPresent(particle -> {
            world.addParticle((ParticleEffect) particle, position[0], position[1], position[2], 0, 0, 0);
        });
    }

    public void renderPreview(WorldRenderContext context){
        Vec3d targetPosition = new Vec3d(position[0], position[1], position[2]);
        Camera camera = context.camera();
        Vec3d transformedPosition = targetPosition.subtract(camera.getPos());
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
        matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);
        Vec3d cameraPosition = camera.getPos();
        Vec3d dir = new Vec3d(cameraPosition.z - targetPosition.z, cameraPosition.y - targetPosition.y, cameraPosition.x - targetPosition.x);
        float angle = (float) (57.29578 * Math.atan2(dir.z, dir.x));
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(-angle));
        double horizontalDistance = Math.sqrt(dir.x * dir.x + dir.z * dir.z);
        float angle2 = (float) (57.29578 * Math.atan2(dir.y, horizontalDistance));
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(angle2));
        matrixStack.translate(-0.5, -0.5, 0);
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        buffer.vertex(positionMatrix, 0, 1, 0).texture(0f, 0f).next();
        buffer.vertex(positionMatrix, 0, 0, 0).texture(0f, 1f).next();
        buffer.vertex(positionMatrix, 1, 0, 0).texture(1f, 1f).next();
        buffer.vertex(positionMatrix, 1, 1, 0).texture(1f, 0f).next();
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderTexture(0, new Identifier(ParticleStudio.MOD_ID, "icon.png"));
        RenderSystem.disableCull();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        tessellator.draw();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.enableCull();
        matrixStack.pop();
    }

}
