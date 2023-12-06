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

    private static final Identifier PARTICLE_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "points/point.png");
    private static final Identifier SELECTED_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "points/selected.png");

    public static void renderParticlePoint(WorldRenderContext context, Vec3d targetPosition){
        renderBillboardTexture(context, targetPosition, PARTICLE_POINT_TEXTURE);
    }

    public static void renderSelectedPoint(WorldRenderContext context, Vec3d targetPosition){
        renderBillboardTexture(context, targetPosition, SELECTED_POINT_TEXTURE);
    }

    public static void renderBillboardTexture(WorldRenderContext context, Vec3d targetPosition, Identifier texture){
        Camera camera = context.camera();
        Vec3d transformedPosition = targetPosition.subtract(camera.getPos());
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0F));
        matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);
        Vec3d cameraPosition = camera.getPos();
        Vec3d dir = new Vec3d(cameraPosition.z - targetPosition.z, cameraPosition.y - targetPosition.y, cameraPosition.x - targetPosition.x);
        double angle = Math.atan2(dir.z, dir.x);
        double angle2 = Math.atan2(dir.y, Math.sqrt(dir.x * dir.x + dir.z * dir.z));
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotation((float) -angle));
        matrixStack.multiply(RotationAxis.NEGATIVE_X.rotation((float) angle2));
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
        tessellator.draw();
        RenderSystem.enableCull();
        matrixStack.pop();
    }

    public static void renderLine(WorldRenderContext context, Vec3d from, Vec3d to){
        Camera camera = context.camera();
        Vec3d transformedPosition = to.subtract(camera.getPos());
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera.getYaw() + 180.0f));
        matrixStack.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);
        Vec3d vector = to.subtract(from);
        double yaw = Math.atan2(vector.z, vector.x);
        double pitch = Math.atan2(vector.y, Math.sqrt(vector.x * vector.x + vector.z * vector.z));
        matrixStack.multiply(RotationAxis.NEGATIVE_Y.rotation((float) yaw));
        matrixStack.multiply(RotationAxis.NEGATIVE_Z.rotation((float) -pitch));
        matrixStack.scale((float) -to.distanceTo(from), 0.02f, 1);
        Matrix4f positionMatrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        buffer.vertex(positionMatrix, 0, 1, 0).color(0f, 0f, 1f, 1f).next();
        buffer.vertex(positionMatrix, 0, 0, 0).color(0f, 0f, 1f, 1f).next();
        buffer.vertex(positionMatrix, 1, 0, 0).color(0f, 0f, 1f, 1f).next();
        buffer.vertex(positionMatrix, 1, 1, 0).color(0f, 0f, 1f, 1f).next();
        RenderSystem.setShader(GameRenderer::getPositionColorProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableCull();
        RenderSystem.depthFunc(GL11.GL_ALWAYS);
        tessellator.draw();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.enableCull();
    }

}
