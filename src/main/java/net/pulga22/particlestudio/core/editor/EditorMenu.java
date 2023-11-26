package net.pulga22.particlestudio.core.editor;

import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.List;

public abstract class EditorMenu {

    private final List<EditorButton> buttons = new ArrayList<>();
    private int currentIndex = 0;

    public abstract void render(DrawContext context);

    public void handleKeyboardInput(int key, Routine routine){
        EditorButton currentButton = buttons.get(currentIndex);
        switch (key){
            case 81 -> currentButton.onQPressed(routine);
            case 69 -> currentButton.onEPressed(routine);
            case 90 -> currentButton.onZPressed(routine);
            case 67 -> currentButton.onCPressed(routine);
        }
    }

    public void handleRightClick(Routine routine){
        buttons.get(currentIndex).onRightClick(routine);
    }

    // 1.0 -> up || -1.0 -> down ||
    public void handleMouseScroll(double vertical){
        currentIndex += (int) Math.floor(vertical) % buttons.size();
    }



}
