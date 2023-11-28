package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorInputHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class TimelineMenu extends EditorMenu {

    public TimelineMenu(EditorMenu previousMenu, EditorInputHandler editorInputHandler) {
        super(previousMenu, editorInputHandler, "Timeline");
        addButton(EditorButton.builder("timeline", "Tick")
                .setAction(Actions.Q, routine -> System.out.println("TODO"), "Previous -1")
                .setAction(Actions.E, routine -> System.out.println("TODO"), "Next +1")
                .setAction(Actions.Z, routine -> System.out.println("TODO"), "Previous -10")
                .setAction(Actions.C, routine -> System.out.println("TODO"), "Next +10").build());
        addButton(EditorButton.builder("timeline", "Onion Skin")
                .setAction(Actions.Q, routine -> System.out.println("TODO"), "++Lower bound")
                .setAction(Actions.E, routine -> System.out.println("TODO"), "--Lower bound")
                .setAction(Actions.Z, routine -> System.out.println("TODO"), "++Upper bound")
                .setAction(Actions.C, routine -> System.out.println("TODO"), "--Upper bound").build());
    }

}
