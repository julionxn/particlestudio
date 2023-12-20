package net.pulga22.particlestudio.core.editor.screen.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class EditorHud implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        accessor.particlestudio$getEditor().render(drawContext, client);

    }

}
