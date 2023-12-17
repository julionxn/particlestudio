package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.routines.Routine;

public class PlayMenu extends EditorMenu {

    public PlayMenu(EditorHandler editorHandler) {
        super(editorHandler, "Play");
        addButton(EditorButton.builder("play/routine", "Routine")
                .setAction(Actions.Q, routine -> routine.getRoutinePlayer().play(), "Play", builder -> builder)
                .setAction(Actions.E, routine -> routine.getRoutinePlayer().pause(), "Pause", builder -> builder)
                .setAction(Actions.Z, routine -> routine.getRoutinePlayer().stop(), "Stop", builder -> builder)
                .setAction(Actions.C, routine -> routine.getRoutinePlayer().restart(), "Restart", builder -> builder)
                .build());
    }

    @Override
    public void onExit(Routine routine) {
        routine.getRoutinePlayer().stop();
    }
}
