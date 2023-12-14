package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.routines.Routine;

public class LinePathMenu extends PathMenu {

    public LinePathMenu(EditorHandler editorHandler) {
        super(editorHandler, "Line Menu");
        addDensity();
        addConfirmAndCancel();
    }

    @Override
    public void render(DrawContext context, MinecraftClient client, Routine currentRoutine) {
        super.render(context, client, currentRoutine);
    }
}
