package net.pulga22.particlestudio.core.editor;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.MainMenu;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class EditorHandler {

    private final PlayerEditor editor;
    private final PlayerEntity player;
    private String currentParticle = "minecraft:happy_villager";
    private final SelectionHandler selectionHandler = new SelectionHandler();
    private final Stack<EditorMenu> menuStack = new Stack<>();
    private boolean scrollActive = true;
    private final Set<KeySubscriber> keySubscribers = new HashSet<>();
    private final Set<ScrollSubscriber> scrollSubscribers = new HashSet<>();
    private final Routine routine;

    public EditorHandler(PlayerEditor playerEditor, PlayerEntity player, Routine routine) {
        this.editor = playerEditor;
        this.player = player;
        this.routine = routine;
        MainMenu menu = new MainMenu(this);
        this.menuStack.push(menu);
        menu.onActive();
    }

    public void onRightClick(Modifiers modifier){

    }

    public void onKey(int key, Modifiers modifier){
        switch (key){
            case 256 -> returnMenu();
            case 75 -> editor.closeEditor();
            default -> keySubscribers.forEach(keySubscriber -> keySubscriber.onKey(key, modifier));
        }
    }

    public void onMouseWheel(double vertical){
        if (!scrollActive) return;
        scrollSubscribers.forEach(scrollSubscribers -> scrollSubscribers.onScroll(vertical));
    }

    public String getCurrentParticle(){
        return currentParticle;
    }

    public void changeCurrentParticle(String particle){
        currentParticle = particle;
    }

    public SelectionHandler getSelectionHandler(){
        return selectionHandler;
    }

    public Vec3d getCurrentPosition(){
        return player.getPos();
    }

    public void changeMenu(EditorMenu menu){
        menuStack.peek().onChange(routine);
        menuStack.push(menu);
        menu.onActive();
    }

    public void returnMenu(){
        EditorMenu currentMenu = menuStack.pop();
        currentMenu.onChange(routine);
        currentMenu.onExit(routine);
        if (menuStack.isEmpty()) {
            editor.closeEditor();
            return;
        }
        menuStack.peek().onActive();
    }

    public void returnTimesMenu(int times){
        for (int i = 0; i < times; i++) {
            returnMenu();
        }
    }

    public EditorMenu getCurrentMenu(){
        return menuStack.peek();
    }

    public void setScrollActive(boolean active){
        scrollActive = active;
    }

    public void subscribeToKey(KeySubscriber menu){
        keySubscribers.add(menu);
    }

    public void unsubscribeToKey(KeySubscriber menu){
        keySubscribers.remove(menu);
    }

    public void subscribeToScroll(ScrollSubscriber menu){
        scrollSubscribers.add(menu);
    }

    public void unsubscribeToScroll(ScrollSubscriber menu){
        scrollSubscribers.remove(menu);
    }

    public Routine getRoutine() {
        return routine;
    }
}
