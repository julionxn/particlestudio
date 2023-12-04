package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.client.MinecraftClient;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.gui.SelectedParticleMenu;
import net.pulga22.particlestudio.core.routines.paths.LinePath;
import net.pulga22.particlestudio.core.routines.paths.Path;

public class ToolsMenu extends EditorMenu {

    public ToolsMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Tools");
        addButton(EditorButton.builder("tools/points", "Puntos")
                .setAction(Actions.Q, routine -> routine.addParticlePoint(editorHandler), "Añadir").build());
        addButton(EditorButton.builder("tools/lines", "Lineas")
                .setAction(Actions.Q, routine -> routine.newPath(new LinePath(editorHandler.getPlayer().getPos())), "Primer punto")
                .setAction(Actions.E, routine -> {
                    Path path = routine.getEditingPath();
                    if (path == null) return;
                    path.setTo(editorHandler.getPlayer().getPos());
                }, "Segundo punto")
                .setAction(Actions.Z, routine -> {
                    Path path = routine.getEditingPath();
                    if (path == null || path.to() == null) return;
                    //todo: open path menu
                }, "Confirmar")
                .setAction(Actions.C, routine -> routine.newPath(null), "Cancelar").build());
        addButton(EditorButton.builder("tools/particles", "Partículas")
                .setAction(Actions.Q, routine -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client == null) return;
                    client.setScreen(new SelectedParticleMenu(editorHandler));
                }, "Cambiar actual").build());
    }

}
