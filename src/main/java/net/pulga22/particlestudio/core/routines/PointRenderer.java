package net.pulga22.particlestudio.core.routines;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.ParticleStudio;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

public class PointRenderer {

    private static final Identifier PARTICLE_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "icon.png");
    private static final Identifier SELECTED_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "active.png");
    private static final Identifier PATH_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "active.png");

    private static void renderBillboardTexture(WorldRenderContext context, Vec3d targetPosition, Identifier texture){
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
        RenderSystem.setShaderTexture(0, texture);
        RenderSystem.disableCull();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        tessellator.draw();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.enableCull();
        matrixStack.pop();
    }

    public static void renderParticlePoint(WorldRenderContext context, Vec3d targetPosition){
        renderBillboardTexture(context, targetPosition, PARTICLE_POINT_TEXTURE);
    }

    public static void renderPathPoint(WorldRenderContext context, Vec3d targetPosition){
        renderBillboardTexture(context, targetPosition, PATH_POINT_TEXTURE);
    }

    public static void renderSelectedPoint(WorldRenderContext context, Vec3d targetPosition){
        renderBillboardTexture(context, targetPosition, SELECTED_POINT_TEXTURE);
    }

}
