package net.pulga22.particlestudio.core.editor;

import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.List;

public class EditorMenu {

    private final List<EditorButton> buttons = new ArrayList<>();
    private int currentIndex = 0;

    public void render(DrawContext context){

    }

    public void handleKeyboardInput(int key, Routine routine){
        EditorButton currentButton = buttons.get(currentIndex);
        switch (key){
            case 81 -> currentButton.onQPress(routine);
            case 69 -> currentButton.onEPress(routine);
            case 90 -> currentButton.onZPress(routine);
            case 67 -> currentButton.onCPress(routine);
        }
    }

    public void handleRightClick(Routine routine){

    }

    // 1.0 -> up || -1.0 -> down ||
    public void handleMouseScroll(double vertical){
        currentIndex += (int) Math.floor(vertical) % buttons.size();
    }

    protected void addButton(EditorButton button){
        this.buttons.add(button);
    }


}
