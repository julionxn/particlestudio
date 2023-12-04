package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.Routine;

public class LinePathMenu extends PathMenu {

    public LinePathMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Line Menu");
        addDensity();
        addConfirmAndCancel(editorHandler);
    }

    @Override
    public void render(DrawContext context, MinecraftClient client, Routine currentRoutine) {
        super.render(context, client, currentRoutine);
    }
}
