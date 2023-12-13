package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.MainMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.PointMenu;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EditorHandler {

    private final PlayerEditor playerEditor;
    private final PlayerEntity player;
    private EditorMenu currentMenu = new MainMenu(this);
    private ScrollSubscriber subscriber;
    private String selectedParticle = "minecraft:happy_villager";
    private final List<ParticlePoint> selectedPoints = new ArrayList<>();
    private final List<ParticlePoint> clipboard = new ArrayList<>();
    private final int NO_MODS = 0;
    private final int SHIFT_MOD = 1;
    private final int CTRL_MOD = 2;

    public EditorHandler(PlayerEditor playerEditor, PlayerEntity player) {
        this.playerEditor = playerEditor;
        this.player = player;
    }

    public void handleRightClick(PlayerEntity player, int mods){
        playerEditor.getCurrentRoutine().ifPresent(routine -> routine.tryToSelectPoint(player).ifPresent(particlePoint -> {
            if (selectedPoints.contains(particlePoint)){
                selectedPoints.remove(particlePoint);
                if (selectedPoints.isEmpty()) changeCurrentMenu(new MainMenu(this), routine);
                return;
            }
            if (selectedPoints.isEmpty()) {
                changeCurrentMenu(new PointMenu(currentMenu, this), routine);
            }
            switch (mods){
                case NO_MODS -> {
                    selectedPoints.clear();
                    selectedPoints.add(particlePoint);
                }
                case SHIFT_MOD, CTRL_MOD -> selectedPoints.add(particlePoint);
            }
        }));
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
        final int ESCAPE_KEY = 256;
        final int k_KEY = 75;
        final int TAB_KEY = 258;
        switch (key){
            case ESCAPE_KEY -> {
                EditorMenu prevMenu = currentMenu.getPreviousMenu();
                if (prevMenu != null){
                    routineOptional.ifPresent(routine -> changeCurrentMenu(prevMenu, routine));
                } else {
                    exitEditor();
                }
            }
            case k_KEY -> exitEditor();
            case TAB_KEY -> handleMouseScroll(1.0);
            default -> routineOptional.ifPresent(routine -> currentMenu.handleKeyboardInput(key, routine));
        }
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

    public List<ParticlePoint> getSelectedPoints(){
        return selectedPoints;
    }

    public PlayerEntity getPlayer(){
        return player;
    }

    public EditorMenu getCurrentMenu(){
        return currentMenu;
    }

    public List<ParticlePoint> getClipboard() { return clipboard; }

    public void setClipboard(List<ParticlePoint> points) {
        this.clipboard.clear();
        this.clipboard.addAll(points);
    }

    public void changeCurrentMenu(EditorMenu menu, Routine routine){
        if (currentMenu instanceof PointMenu) {
            selectedPoints.clear();
        }
        currentMenu.onExit(routine);
        this.currentMenu = menu;
    }

    public void subscribeToScroll(ScrollSubscriber subscriber){
        this.subscriber = subscriber;
    }

    public void unsubscribeToScroll(ScrollSubscriber menu){
        if (subscriber != menu) return;
        subscriber = null;
    }

}
