package net.pulga22.particlestudio.core.editor.screen.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class LoadingHud implements HudRenderCallback {

    private final Identifier LOADING_FRAME_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "loading_frame.png");
    private final Identifier FILL_TEXTURE = new Identifier(ParticleStudio.MOD_ID, "fill.png");

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        PlayerEditor.LoadingInfo loadingInfo = accessor.particlestudio$getEditor().loadingInfo();
        if (!loadingInfo.loading()) return;
        int width  = (int) (loadingInfo.percent() * 100);
        context.drawTexture(LOADING_FRAME_TEXTURE, context.getScaledWindowWidth() - 114, context.getScaledWindowHeight() - 34, 0, 0, 0, 104, 24, 104, 24);
        context.drawTexture(FILL_TEXTURE, context.getScaledWindowWidth() - 112, context.getScaledWindowHeight() - 32, 0, 0, 0, width, 20, width, 20);
    }

}
