package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.Routine;

public class PathMenu extends EditorMenu {

    public PathMenu(EditorMenu previousMenu, EditorHandler editorHandler, String name) {
        super(previousMenu, editorHandler, name);
    }

    @Override
    public void render(DrawContext context, MinecraftClient client, Routine currentRoutine) {
        super.render(context, client, currentRoutine);
    }

    protected void changeDensity(Routine routine, float in){
        routine.getEditingPath().ifPresent(path -> {
            float density = path.getDensity();
            if (density + in <= 0) return;
            path.changeDensity(density + in);
        });
    }

    protected void confirm(Routine routine){
        routine.getEditingPath().ifPresent(path -> {
            path.apply(routine, editorHandler.getSelectedParticle());
            routine.clearRoutine();
            editorHandler.changeCurrentMenu(getPreviousMenu().getPreviousMenu(), routine);
        });
    }

    protected void cancel(EditorHandler editorHandler, Routine routine){
        routine.clearRoutine();
        editorHandler.changeCurrentMenu(getPreviousMenu(), routine);
    }

    protected void addDensity(){
        addButton(EditorButton.builder("points/paths/density", "Change density")
                .setAction(Actions.Q, routine -> changeDensity(routine, -0.1f), "Density -0.1")
                .setAction(Actions.E, routine -> changeDensity(routine, 0.1f), "Density +0.1")
                .setAction(Actions.Z, routine -> changeDensity(routine, -1), "Density -1")
                .setAction(Actions.C, routine -> changeDensity(routine, 1), "Density +1")
                .build());
    }

    protected void addConfirmAndCancel(EditorHandler editorHandler){
        addButton(EditorButton.builder("points/paths/actions", "Actions")
                .setAction(Actions.Q, this::confirm, "Confirm")
                .setAction(Actions.E, routine -> cancel(editorHandler, routine), "Cancel")
                .build());
    }

    @Override
    public void onExit(Routine routine) {
        super.onExit(routine);
        routine.clearRoutine();
    }
}
