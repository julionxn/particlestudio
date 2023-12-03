package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.MainMenu;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.Optional;

public class EditorHandler {

    private final PlayerEditor playerEditor;
    private final PlayerEntity player;
    private EditorMenu currentMenu = new MainMenu(this);
    private ScrollSubscriber subscriber;
    private String selectedParticle = "minecraft:happy_villager";

    public EditorHandler(PlayerEditor playerEditor, PlayerEntity player) {
        this.playerEditor = playerEditor;
        this.player = player;
    }

    public void handleRightClick(){
        playerEditor.getCurrentRoutine().ifPresent(routine -> currentMenu.handleRightClick(routine));
    }

    public void subscribeToScroll(ScrollSubscriber subscriber){
        this.subscriber = subscriber;
    }

    public void unsubscribeToScroll(ScrollSubscriber menu){
        if (subscriber != menu) return;
        subscriber = null;
    }

    public void handleMouseScroll(double vertical){
        if (subscriber != null) {
            subscriber.handleScroll(vertical);
            return;
        }
        currentMenu.handleMouseScroll(vertical);
    }

    public void handleKeyboard(int key){
        Optional<Routine> routineOptional = playerEditor.getCurrentRoutine();
        if (key == 256){
            EditorMenu prevMenu = currentMenu.getPreviousMenu();
            if (prevMenu != null){
                routineOptional.ifPresent(routine -> changeCurrentMenu(prevMenu, routine));
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

    public void changeCurrentMenu(EditorMenu menu, Routine routine){
        currentMenu.onExit(routine);
        this.currentMenu = menu;
    }

}
