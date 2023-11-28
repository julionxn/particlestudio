package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorInputHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class MainMenu extends EditorMenu {

    public MainMenu(EditorInputHandler editorInputHandler) {
        super(null, editorInputHandler, "Menú principal");
        addButton(EditorButton.builder("main_menu", "Timeline")
                .setAction(Actions.Q,
                        routine -> editorInputHandler.changeCurrentMenu(new TimelineMenu(this, editorInputHandler)),
                        "Entrar").build());
        addButton(EditorButton.builder("main_menu", "Herramientas")
                .setAction(Actions.Q,
                        routine -> editorInputHandler.changeCurrentMenu(new ToolsMenu(this, editorInputHandler)),
                        "Entrar").build());
    }

}
