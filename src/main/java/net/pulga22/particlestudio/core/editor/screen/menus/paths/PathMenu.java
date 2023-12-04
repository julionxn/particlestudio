package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.paths.Path;
import net.pulga22.particlestudio.core.routines.Routine;

public class PathMenu extends EditorMenu {

    public PathMenu(EditorMenu previousMenu, EditorHandler editorHandler, String name) {
        super(previousMenu, editorHandler, name);
    }

    protected void changeDensity(Routine routine, int in){
        Path path = routine.getEditingPath();
        if (path == null) return;
        int density = path.getDensity();
        if (density + in < 1) return;
        path.changeDensity(density + in);
    }

    protected void confirm(Routine routine){
        Path path = routine.getEditingPath();
        if (path == null) return;
        path.transform(routine);
    }

    protected void cancel(EditorHandler editorHandler, Routine routine){
        routine.newPath(null);
        editorHandler.changeCurrentMenu(getPreviousMenu(), routine);
    }

    protected void addDensity(){
        addButton(EditorButton.builder("points/paths/density", "Change density")
                .setAction(Actions.Q, routine -> changeDensity(routine, 1), "Density +1")
                .setAction(Actions.E, routine -> changeDensity(routine, -1), "Density -1")
                .setAction(Actions.Z, routine -> changeDensity(routine, 10), "Density +10")
                .setAction(Actions.C, routine -> changeDensity(routine, -10), "Density -10")
                .build());
    }

    protected void addConfirmAndCancel(EditorHandler editorHandler){
        addButton(EditorButton.builder("points/paths/confirm", "Confirm")
                .setAction(Actions.Q, this::confirm, "Confirm")
                .build());
        addButton(EditorButton.builder("points/paths/cancel", "Cancel")
                .setAction(Actions.Q, routine -> cancel(editorHandler, routine), "Cancel")
                .build());
    }

}
