package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.routines.paths.CurvePath;

public class CurvePathMenu extends PathMenu {
    public CurvePathMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Curve Path");
        addDensity();
        addButton(EditorButton.builder("points/paths/curve", "Control points")
                .setAction(Actions.Q, routine -> {
                    PlayerEntity player = editorHandler.getPlayer();
                    routine.getEditingPath().ifPresent(path -> {
                        CurvePath curvePath = (CurvePath) path;
                        curvePath.setFirstControlPoint(player.getPos());
                    });
                }, "Set 1st control point")
                .setAction(Actions.E, routine -> {
                    PlayerEntity player = editorHandler.getPlayer();
                    routine.getEditingPath().ifPresent(path -> {
                        CurvePath curvePath = (CurvePath) path;
                        curvePath.setSecondControlPoint(player.getPos());
                    });
                }, "Set 2nd control point")
                .build());
        addConfirmAndCancel(editorHandler);
    }


}
