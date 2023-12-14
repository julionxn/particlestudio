package net.pulga22.particlestudio.core.editor.screen.menus.paths;

import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.routines.paths.CurvePath;

public class CurvePathMenu extends PathMenu {

    public CurvePathMenu(EditorHandler editorHandler) {
        super(editorHandler, "Curve Path");
        addDensity();
        addButton(EditorButton.builder("points/paths/curve", "Control points")
                .setAction(Actions.Q, routine -> {
                    routine.getEditingPath().ifPresent(path -> {
                        CurvePath curvePath = (CurvePath) path;
                        curvePath.setFirstControlPoint(editorHandler.getCurrentPosition());
                    });
                }, "Set 1st control point")
                .setAction(Actions.E, routine -> {
                    routine.getEditingPath().ifPresent(path -> {
                        CurvePath curvePath = (CurvePath) path;
                        curvePath.setSecondControlPoint(editorHandler.getCurrentPosition());
                    });
                }, "Set 2nd control point")
                .build());
        addConfirmAndCancel();
    }


}
