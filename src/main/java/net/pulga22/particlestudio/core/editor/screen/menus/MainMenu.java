package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;

public class MainMenu extends EditorMenu {

    public MainMenu(EditorHandler editorHandler) {
        super(editorHandler, "Main Menu");
        addButton(EditorButton.builder("timeline", "Timeline")
                .setAction(Actions.Q, routine -> editorHandler.changeMenu(new TimelineMenu(editorHandler)),
                        "Entrar", builder -> builder).build());
        addButton(EditorButton.builder("tools", "Tools")
                .setAction(Actions.Q, routine -> editorHandler.changeMenu(new ToolsMenu(editorHandler)),
                        "Entrar", builder -> builder).build());
        addButton(EditorButton.builder("play", "Play")
                .setAction(Actions.Q, routine -> editorHandler.changeMenu(new PlayMenu(editorHandler)),
                        "Entrar", builder -> builder).build());
        addButton(EditorButton.builder("routine", "Routine")
                .setAction(Actions.Q, routine -> {
                    routine.prepareToSave(editorHandler);
                }, "Save", builder -> builder)
                .setAction(Actions.C, routine -> System.out.println("DELETE"), "Delete", builder -> builder)
                .build());
    }

}
