package net.pulga22.particlestudio.core.routines.paths;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.routines.PointRenderer;

public record GhostPoint(int tick, Vec3d pos) {

    private static final Identifier GHOST_POINT_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "points/ghost.png");

    public void render(WorldRenderContext context) {
        PointRenderer.renderBillboardTexture(context, pos, GHOST_POINT_TEXTURE);
    }

}
