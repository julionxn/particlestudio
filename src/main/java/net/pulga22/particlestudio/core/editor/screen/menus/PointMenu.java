package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.paths.CurvePathMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.paths.LinePathMenu;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.paths.CurvePath;
import net.pulga22.particlestudio.core.routines.paths.LinePath;

import java.util.List;

public class PointMenu extends EditorMenu {

    public PointMenu(EditorHandler editorHandler) {
        super(editorHandler, "Point edit");
        addButton(EditorButton.builder("points/center", "Center to block")
                .setAction(Actions.Q, routine -> editorHandler.getSelectionHandler().get().forEach(this::centerToBlock), "Center")
                .build());
        addButton(EditorButton.builder("points/x", "X axis")
                .setAction(Actions.Q, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.X, point, -0.1)), "X - 0.1")
                .setAction(Actions.E, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.X, point, 0.1)), "X + 0.1")
                .setAction(Actions.Z, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.X, point, -1.0)), "X - 1.0")
                .setAction(Actions.C, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.X, point, 1.0)), "X + 1.0")
                .build());
        addButton(EditorButton.builder("points/y", "Y axis")
                .setAction(Actions.Q, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Y, point, -0.1)), "Y - 0.1")
                .setAction(Actions.E, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Y, point, 0.1)), "Y + 0.1")
                .setAction(Actions.Z, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Y, point, -1.0)), "Y - 1.0")
                .setAction(Actions.C, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Y, point, 1.0)), "Y + 1.0")
                .build());
        addButton(EditorButton.builder("points/z", "Z axis")
                .setAction(Actions.Q, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Z, point, -0.1)), "Z - 0.1")
                .setAction(Actions.E, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Z, point, 0.1)), "Z + 0.1")
                .setAction(Actions.Z, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Z, point, -1.0)), "Z - 1.0")
                .setAction(Actions.C, routine -> editorHandler.getSelectionHandler().get().forEach(point -> modifyPosition(Axis.Z, point, 1.0)), "Z + 1.0")
                .build());
        addButton(EditorButton.builder("points/paths", "Paths")
                .setAction(Actions.Q, routine -> {
                    List<ParticlePoint> points = editorHandler.getSelectionHandler().get();
                    if (points.size() != 2) return;
                    routine.newPath(new LinePath(points.get(0), points.get(1)));
                    editorHandler.changeMenu(new LinePathMenu(editorHandler));
                }, "Line")
                .setAction(Actions.E, routine -> {
                    List<ParticlePoint> points = editorHandler.getSelectionHandler().get();
                    if (points.size() != 2) return;
                    routine.newPath(new CurvePath(points.get(0), points.get(1)));
                    editorHandler.changeMenu(new CurvePathMenu(editorHandler));
                }, "Curve")
                .build());
        addButton(EditorButton.builder("points/copy", "Copy")
                .setAction(Actions.Q, routine -> System.out.println("HOLA"), "Copy").build());
    }

    private void centerToBlock(ParticlePoint particlePoint){
        double[] position = particlePoint.position;
        particlePoint.position[0] = Math.floor(position[0]) + 0.5;
        particlePoint.position[1] = Math.floor(position[1]) + 0.5;
        particlePoint.position[2] = Math.floor(position[2]) + 0.5;
    }

    private void modifyPosition(Axis axis, ParticlePoint particlePoint, double in){
        switch (axis){
            case X -> particlePoint.position[0] += in;
            case Y -> particlePoint.position[1] += in;
            case Z -> particlePoint.position[2] += in;
        }
    }

    private enum Axis{
        X, Y, Z
    }

}
