package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.paths.Path;
import net.pulga22.particlestudio.core.routines.Routine;

public class PathMenu extends EditorMenu {

    public PathMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Path Menu");
    }

    protected void changeDuration(Routine routine, int in){
        Path path = routine.getEditingPath();
        if (path == null) return;
        int duration = path.getDuration();
        if (duration + in < 1) return;
        path.changeDuration(duration + in);
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

}
