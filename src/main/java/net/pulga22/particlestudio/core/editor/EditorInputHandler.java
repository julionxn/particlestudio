package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.screen.menus.TestMenu;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class EditorInputHandler {

    private final PlayerEditor playerEditor;
    private final PlayerEntity player;
    private EditorMenu currentMenu = new TestMenu(this);

    public EditorInputHandler(PlayerEditor playerEditor, PlayerEntity player) {
        this.playerEditor = playerEditor;
        this.player = player;
    }

    public void handleRightClick(){
        playerEditor.getCurrentRoutine().ifPresent(routine -> currentMenu.handleRightClick(routine));
    }

    public void handleMouseScroll(double vertical){
        currentMenu.handleMouseScroll(vertical);
    }

    public void handleKeyboard(int key){
        System.out.println(key);
        if (key == 256 && currentMenu.getPreviousMenu() != null){
            currentMenu = currentMenu.getPreviousMenu();
            return;
        }
        //K button
        if (key == 75) {
            PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
            accessor.particlestudio$setEditing(false);
            return;
        }

        playerEditor.getCurrentRoutine().ifPresent(routine -> currentMenu.handleKeyboardInput(key, routine));
    }

    public EditorMenu getCurrentMenu(){
        return currentMenu;
    }

    public void changeCurrentMenu(EditorMenu menu){
        this.currentMenu = menu;
    }

}
