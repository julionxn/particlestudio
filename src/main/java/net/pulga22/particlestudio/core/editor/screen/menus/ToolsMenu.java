package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.client.MinecraftClient;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.gui.SelectedParticleMenu;

public class ToolsMenu extends EditorMenu {

    public ToolsMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Tools");
        addButton(EditorButton.builder("tools", "Puntos")
                .setAction(Actions.Q, routine -> routine.addParticlePoint(editorHandler), "Añadir").build());
        addButton(EditorButton.builder("tools", "Lineas")
                .setAction(Actions.Q, routine -> System.out.println("TODO"), "Primer punto")
                .setAction(Actions.E, routine -> System.out.println("TODO"), "Segundo punto")
                .setAction(Actions.Z, routine -> System.out.println("TODO"), "Confirmar").build());
        addButton(EditorButton.builder("tools", "Partículas")
                .setAction(Actions.Q, routine -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client == null) return;
                    client.setScreen(new SelectedParticleMenu(editorHandler));
                }, "Cambiar actual").build());
    }

}
