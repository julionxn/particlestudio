package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.gui.SelectedParticleMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.MainMenu;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class EditorHandler {

    private final PlayerEditor playerEditor;
    private final PlayerEntity player;
    private EditorMenu currentMenu = new MainMenu(this);
    private SelectedParticleMenu subscriber;
    private String selectedParticle = "minecraft:happy_villager";

    public EditorHandler(PlayerEditor playerEditor, PlayerEntity player) {
        this.playerEditor = playerEditor;
        this.player = player;
    }

    public void handleRightClick(){
        playerEditor.getCurrentRoutine().ifPresent(routine -> currentMenu.handleRightClick(routine));
    }

    public void subscribeToScroll(SelectedParticleMenu menu){
        subscriber = menu;
    }

    public void unsubscribeToScroll(SelectedParticleMenu menu){
        if (subscriber != menu) return;
        subscriber = null;
    }

    public void handleMouseScroll(double vertical){
        if (subscriber != null) {
            subscriber.handleMouseScroll(vertical);
            return;
        }
        currentMenu.handleMouseScroll(vertical);
    }

    public void handleKeyboard(int key){
        if (key == 256){
            EditorMenu prevMenu = currentMenu.getPreviousMenu();
            if (prevMenu != null){
                currentMenu = currentMenu.getPreviousMenu();
            } else {
                exitEditor();
            }
            return;
        }
        if (key == 75) {
            exitEditor();
            return;
        }
        if (key == 258) {
            handleMouseScroll(1.0);
            return;
        }
        playerEditor.getCurrentRoutine().ifPresent(routine -> currentMenu.handleKeyboardInput(key, routine));
    }

    private void exitEditor(){
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        accessor.particlestudio$setEditing(false);
    }

    public void changeSelectedParticle(String particle){
        selectedParticle = particle;
    }

    public String getSelectedParticle(){
        return selectedParticle;
    }

    public PlayerEntity getPlayer(){
        return player;
    }


    public EditorMenu getCurrentMenu(){
        return currentMenu;
    }

    public void changeCurrentMenu(EditorMenu menu){
        this.currentMenu = menu;
    }

}
