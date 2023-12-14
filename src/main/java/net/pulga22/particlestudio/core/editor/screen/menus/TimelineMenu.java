package net.pulga22.particlestudio.core.editor.screen.menus;

import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;

public class TimelineMenu extends EditorMenu {

    public TimelineMenu(EditorHandler editorHandler) {
        super(editorHandler, "Timeline");
        addButton(EditorButton.builder("timeline/tick", "Tick")
                .setAction(Actions.Q, routine -> routine.getTimeline().adjustFrame(-1), "Previous -1",
                        builder -> builder.setShiftAction(routine -> routine.getTimeline().adjustFrame(-10), "Previous -10"))
                .setAction(Actions.E, routine -> routine.getTimeline().adjustFrame(1), "Next +1",
                        builder -> builder.setShiftAction(routine -> routine.getTimeline().adjustFrame(10), "Next +10"))
                .build());
        addButton(EditorButton.builder("timeline/onion", "Onion Skin")
                .setAction(Actions.Q, routine -> routine.getTimeline().adjustOnionLowerBound(-1), "Lower bound -1",
                        builder -> builder.setShiftAction(routine -> routine.getTimeline().adjustOnionLowerBound(-10), "Lower bound -10"))
                .setAction(Actions.E, routine -> routine.getTimeline().adjustOnionLowerBound(1), "Lower bound +1",
                        builder -> builder.setShiftAction(routine -> routine.getTimeline().adjustOnionLowerBound(10), "Lower bound +10"))
                .setAction(Actions.Z, routine -> routine.getTimeline().adjustOnionUpperBound(-1), "Upper bound -1",
                        builder -> builder.setShiftAction(routine -> routine.getTimeline().adjustOnionUpperBound(-10), "Upper bound -10"))
                .setAction(Actions.C, routine -> routine.getTimeline().adjustOnionUpperBound(1), "Upper bound +1",
                        builder -> builder.setShiftAction(routine -> routine.getTimeline().adjustOnionUpperBound(10), "Upper bound +10")).build());
    }

}
