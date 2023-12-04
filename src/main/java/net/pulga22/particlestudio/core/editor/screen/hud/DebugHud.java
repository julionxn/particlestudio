package net.pulga22.particlestudio.core.editor.screen.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.core.routines.Timeline;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class DebugHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext context, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        PlayerEntity entity = client.player;
        if (entity == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) entity;
        PlayerEditor editor = accessor.particlestudio$getEditor();
        editor.getCurrentRoutine().ifPresent(routine -> {
            Timeline timeline = routine.getTimeline();
            drawText(context, client, "CURRENTEDIT: " + timeline.getCurrentEditingTick(), 0);
            drawText(context, client, "DISPLAY: " + timeline.displayLength(), 20);
            drawText(context, client, "ACTUAL: " + timeline.length(), 40);
            drawText(context, client, "LOWER: " + timeline.onionLowerBound(), 60);
            drawText(context, client, "UPPER: " + timeline.onionUpperBound(), 80);
        });
    }

    private void drawText(DrawContext context, MinecraftClient client, String text, int offset){
        int x = context.getScaledWindowWidth() - 100;
        context.drawText(client.textRenderer, text, x, 20 + offset, 0xffffff, false);
    }

}
