package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorInputHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class ToolsMenu extends EditorMenu {

    public ToolsMenu(EditorMenu previousMenu, EditorInputHandler editorInputHandler) {
        super(previousMenu, editorInputHandler, "Tools");
        addButton(EditorButton.builder("tools", "Puntos")
                .setAction(Actions.Q, routine -> System.out.println("TODO"), "Añadir").build());
        addButton(EditorButton.builder("tools", "Lineas")
                .setAction(Actions.Q, routine -> System.out.println("TODO"), "Primer punto")
                .setAction(Actions.E, routine -> System.out.println("TODO"), "Segundo punto")
                .setAction(Actions.Z, routine -> System.out.println("TODO"), "Confirmar").build());
        addButton(EditorButton.builder("tools", "Partículas")
                .setAction(Actions.Q, routine -> System.out.println("TODO"), "Cambiar actual").build());
    }

}
