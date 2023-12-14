package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class MainMenu extends EditorMenu {

    public MainMenu(EditorHandler editorHandler) {
        super(editorHandler, "Menú principal");
        addButton(EditorButton.builder("timeline", "Timeline")
                .setAction(Actions.Q,
                        routine -> editorHandler.changeMenu(new TimelineMenu(editorHandler)),
                        "Entrar", builder -> builder).build());
        addButton(EditorButton.builder("tools", "Tools")
                .setAction(Actions.Q,
                        routine -> editorHandler.changeMenu(new ToolsMenu(editorHandler)),
                        "Entrar", builder -> builder).build());
        addButton(EditorButton.builder("play", "Play")
                .setAction(Actions.Q,
                        routine -> editorHandler.changeMenu(new PlayMenu(editorHandler)),
                        "Entrar", builder -> builder).build());
    }

}
