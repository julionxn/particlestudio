package net.pulga22.particlestudio.core.editor.screen.hud;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.Optional;

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
        Optional<Routine> routineOptional = playerEditor.getCurrentRoutine();
        if (routineOptional.isEmpty()) return;
        Routine routine = routineOptional.get();

        int length = routine.length();
        int currentEditingTick = routine.getCurrentEditingTick();
        drawContext.drawCenteredTextWithShadow(client.textRenderer,
                "Tick: " + currentEditingTick + "/" + length + " (" + tickToSec(currentEditingTick) + "s:" + tickToSec(length) + "s)",
                client.getWindow().getScaledWidth() / 2,
                client.getWindow().getScaledHeight() - 32,
                0xffffff);

        playerEditor.getInputHandler().getCurrentMenu().render(drawContext, client);

    }

    private String tickToSec(int tick){
        float secs = tick / 20f;
        return String.format("%.2f", secs);
    }

}
