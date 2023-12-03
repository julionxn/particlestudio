package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class TimelineMenu extends EditorMenu {

    public TimelineMenu(EditorMenu previousMenu, EditorHandler editorHandler) {
        super(previousMenu, editorHandler, "Timeline");
        addButton(EditorButton.builder("timeline/tick", "Tick")
                .setAction(Actions.Q, routine -> routine.adjustFrame(-1), "Anterior -1")
                .setAction(Actions.E, routine -> routine.adjustFrame(1), "Siguiente +1")
                .setAction(Actions.Z, routine -> routine.adjustFrame(-10), "Anterior -10")
                .setAction(Actions.C, routine -> routine.adjustFrame(10), "Siguiente +10").build());
        addButton(EditorButton.builder("timeline/onion", "Onion Skin")
                .setAction(Actions.Q, routine -> routine.adjustOnionLowerBound(1), "Limite inferior +1")
                .setAction(Actions.E, routine -> routine.adjustOnionLowerBound(-1), "Limite inferior -1")
                .setAction(Actions.Z, routine -> routine.adjustOnionUpperBound(1), "Limite superior +1")
                .setAction(Actions.C, routine -> routine.adjustOnionUpperBound(-1), "Limite superior -1").build());
    }

}
