package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class PointMenu extends EditorMenu {

    public PointMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Point edit");
        addButton(EditorButton.builder("points/center", "Center to block")
                .setAction(Actions.Q, routine -> System.out.println("xd"), "Center")
                .build());
        addButton(EditorButton.builder("points/x", "X axis")
                .setAction(Actions.Q, routine -> System.out.println("xd"), "X + 0.1")
                .setAction(Actions.E, routine -> System.out.println("xd"), "X - 0.1")
                .setAction(Actions.Z, routine -> System.out.println("xd"), "X + 1.0")
                .setAction(Actions.C, routine -> System.out.println("xd"), "X - 1.0")
                .build());
        addButton(EditorButton.builder("points/y", "Y axis")
                .setAction(Actions.Q, routine -> System.out.println("xd"), "Y + 0.1")
                .setAction(Actions.E, routine -> System.out.println("xd"), "Y - 0.1")
                .setAction(Actions.Z, routine -> System.out.println("xd"), "Y + 1.0")
                .setAction(Actions.C, routine -> System.out.println("xd"), "Y - 1.0")
                .build());
        addButton(EditorButton.builder("points/z", "Z axis")
                .setAction(Actions.Q, routine -> System.out.println("xd"), "Z + 0.1")
                .setAction(Actions.E, routine -> System.out.println("xd"), "Z - 0.1")
                .setAction(Actions.Z, routine -> System.out.println("xd"), "Z + 1.0")
                .setAction(Actions.C, routine -> System.out.println("xd"), "Z - 1.0")
                .build());
    }

}
