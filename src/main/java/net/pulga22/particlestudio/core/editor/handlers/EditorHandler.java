package net.pulga22.particlestudio.core.editor.handlers;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.MainMenu;
import net.pulga22.particlestudio.core.editor.screen.menus.PointMenu;
import net.pulga22.particlestudio.core.routines.ParticlePoint;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.Keys;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

public class EditorHandler {

    private final PlayerEditor editor;
    private final PlayerEntity player;
    private String currentParticle = "minecraft:happy_villager";
    private final Stack<EditorMenu> menuStack = new Stack<>();
    private boolean scrollActive = true;
    private final Set<KeySubscriber> keySubscribers = new HashSet<>();
    private final Set<ScrollSubscriber> scrollSubscribers = new HashSet<>();
    private final Routine routine;
    private Modifiers currentPhase = Modifiers.NONE;

    public EditorHandler(PlayerEditor playerEditor, PlayerEntity player, Routine routine) {
        this.editor = playerEditor;
        this.player = player;
        this.routine = routine;
        MainMenu menu = new MainMenu(this);
        this.menuStack.push(menu);
        menu.onActive(routine);
    }

    public void close(){
        editor.closeEditor();
    }

    public void onRightClick(Modifiers modifier){
        List<ParticlePoint> selectedPoints = routine.getSelectionHandler().get();
        routine.tryToSelectPoint(player).ifPresent(particlePoint -> {
            if (selectedPoints.contains(particlePoint)){
                selectedPoints.remove(particlePoint);
                if (selectedPoints.isEmpty()) returnMenu();
                return;
            }
            if (selectedPoints.isEmpty()) {
                changeMenu(new PointMenu(this));
            }
            switch (modifier){
                case NONE -> {
                    selectedPoints.clear();
                    selectedPoints.add(particlePoint);
                }
                case SHIFT, CTRL -> selectedPoints.add(particlePoint);
            }
        });
    }

    public void onKey(int key){
        switch (key){
            case Keys.ESC -> returnMenu();
            case Keys.K -> editor.closeEditor();
            default -> keySubscribers.forEach(keySubscriber -> keySubscriber.onKey(key, currentPhase));
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

    public Vec3d getCurrentPosition(){
        return player.getPos();
    }

    public void changeMenu(EditorMenu menu){
        menuStack.peek().onChange(routine);
        menuStack.push(menu);
        menu.onActive(routine);
    }

    public void returnMenu(){
        EditorMenu currentMenu = menuStack.pop();
        currentMenu.onChange(routine);
        currentMenu.onExit(routine);
        if (menuStack.isEmpty()) {
            editor.closeEditor();
            return;
        }
        menuStack.peek().onActive(routine);
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

    public void setCurrentPhase(Modifiers currentPhase) {
        this.currentPhase = currentPhase;
    }

    public Modifiers getCurrentPhase(){
        return currentPhase;
    }

    public void prepareSaveRoutine(Routine routine){
        ClientPlayNetworking.send(AllPackets.C2S_REQUEST_ROUTINE_SAVE, PacketByteBufs.create().writeUuid(routine.uuid));
    }

    public void saveRoutine(Routine routine){
        Routine.serializeInChunks(routine).ifPresent(data -> {
            for (int i = 0; i < data.length; i++) {
                byte[] datum = data[i];
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(i);
                buf.writeBoolean(i == data.length - 1).writeUuid(routine.uuid);
                buf.writeByteArray(datum);
                ClientPlayNetworking.send(AllPackets.C2S_SEND_ROUTINE_SAVE_CHUNK, buf);
            }
        });
    }

}
