package net.pulga22.particlestudio.core.editor.screen.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class EditorHud implements HudRenderCallback {

    private MinecraftClient client;
    private PlayerEntity player;
    private PlayerEditor playerEditor;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if (client == null) {
            client = MinecraftClient.getInstance();
            return;
        }
        if (player == null){
            player = client.player;
            return;
        }
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        if (playerEditor == null) playerEditor = accessor.particlestudio$getEditor();

        playerEditor.render(drawContext, client);

    }



}
