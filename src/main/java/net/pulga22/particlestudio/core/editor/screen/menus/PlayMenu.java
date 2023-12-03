package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.Routine;

public class PlayMenu extends EditorMenu {

    public PlayMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Play");
        addButton(EditorButton.builder("play/routine", "Routine")
                .setAction(Actions.Q, Routine::play, "Play")
                .setAction(Actions.E, Routine::pause, "Pause")
                .setAction(Actions.Z, Routine::stop, "Stop")
                .setAction(Actions.C, Routine::restart, "Restart")
                .build());
    }

    @Override
    public void onExit(Routine routine) {
        routine.stop();
    }
}
