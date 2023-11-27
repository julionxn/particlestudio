package net.pulga22.particlestudio.core.editor;

import net.pulga22.particlestudio.core.editor.screen.menus.TestMenu;

import java.util.ArrayList;
import java.util.List;

public class EditorInputHandler {

    private final PlayerEditor playerEditor;
    private final List<EditorMenu> menus = new ArrayList<>(){{
        add(new TestMenu());
    }};
    private int currentIndex = 0;

    public EditorInputHandler(PlayerEditor playerEditor) {
        this.playerEditor = playerEditor;
    }

    public void handleRightClick(){
        playerEditor.getCurrentRoutine().ifPresent(routine -> menus.get(currentIndex).handleRightClick(routine));
    }

    public void handleMouseScroll(double vertical){
        menus.get(currentIndex).handleMouseScroll(vertical);
    }

    public void handleKeyboard(int key){
        if (currentIndex > 0 && key == 256){
            currentIndex--;
            return;
        }
        playerEditor.getCurrentRoutine().ifPresent(routine -> menus.get(currentIndex).handleKeyboardInput(key, routine));
    }

    public EditorMenu getCurrentMenu(){
        return menus.get(currentIndex);
    }

}
