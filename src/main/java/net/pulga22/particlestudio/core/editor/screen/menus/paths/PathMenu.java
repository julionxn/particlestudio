package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.Routine;

public class PathMenu extends EditorMenu {

    public PathMenu(EditorHandler editorHandler, String name) {
        super(editorHandler, name);
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
            path.apply(routine, editorHandler.getCurrentParticle());
            routine.clearPath();
            editorHandler.returnTimesMenu(2);
        });
    }

    protected void cancel(Routine routine){
        routine.clearPath();
        editorHandler.returnMenu();
    }

    protected void addDensity(){
        addButton(EditorButton.builder("points/paths/density", "Change density")
                .setAction(Actions.Q, routine -> changeDensity(routine, -1f), "Density -1",
                        builder -> builder.setShiftAction(routine -> changeDensity(routine, -10), "Density -10")
                                .setCtrlAction(routine -> changeDensity(routine, -0.1f), "Density -0.1"))
                .setAction(Actions.E, routine -> changeDensity(routine, 1f), "Density +1",
                        builder -> builder.setShiftAction(routine -> changeDensity(routine, 10), "Density +10")
                                .setCtrlAction(routine -> changeDensity(routine, 0.1f), "Density +0.1"))
                .build());
    }

    protected void addConfirmAndCancel(){
        addButton(EditorButton.builder("points/paths/actions", "Actions")
                .setAction(Actions.Q, this::confirm, "Confirm", builder -> builder)
                .setAction(Actions.E, this::cancel, "Cancel", builder -> builder)
                .build());
    }

    @Override
    public void onExit(Routine routine) {
        super.onExit(routine);
        routine.clearPath();
        routine.getSelectionHandler().clear();
    }
}
