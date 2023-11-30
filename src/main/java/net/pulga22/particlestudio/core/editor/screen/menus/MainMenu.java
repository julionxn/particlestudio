package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class MainMenu extends EditorMenu {

    public MainMenu(EditorHandler editorHandler) {
        super(null, editorHandler, "Menú principal");
        addButton(EditorButton.builder("main_menu", "Timeline")
                .setAction(Actions.Q,
                        routine -> editorHandler.changeCurrentMenu(new TimelineMenu(this, editorHandler)),
                        "Entrar").build());
        addButton(EditorButton.builder("main_menu", "Herramientas")
                .setAction(Actions.Q,
                        routine -> editorHandler.changeCurrentMenu(new ToolsMenu(this, editorHandler)),
                        "Entrar").build());
    }

}
