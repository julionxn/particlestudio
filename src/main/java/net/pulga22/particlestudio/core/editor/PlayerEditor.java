package net.pulga22.particlestudio.core.editor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlayerEditor {

    private final HashMap<String, Routine> loadedRoutines = new HashMap<>();
    private Set<String> routineNames = new HashSet<>();
    private Routine currentRoutine = null;
    private EditorHandler editorHandler;
    private final PlayerEntity player;

    public PlayerEditor(PlayerEntity player){
        this.player = player;
    }

    public EditorHandler getHandler(){
        return editorHandler;
    }

    public void loadRoutine(String name, Routine routine){
        this.loadedRoutines.put(name, routine);
    }

    public Optional<Routine> getRoutine(String name){
        if (loadedRoutines.containsKey(name)) return Optional.of(loadedRoutines.get(name));
        return Optional.empty();
    }

    public void setActiveRoutine(Routine routine){
        currentRoutine = routine;
    }

    public void createRoutine(String name){
        if (routineNames.contains(name)) return;
        loadRoutine(name, new Routine());
        routineNames.add(name);
    }

    public void openEditor(){
        if (currentRoutine == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        accessor.particlestudio$setEditing(true);
        editorHandler = new EditorHandler(this, player, currentRoutine);
    }

    public void closeEditor(){
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        accessor.particlestudio$setEditing(false);
        editorHandler = null;
    }

    public Optional<Routine> getCurrentRoutine(){
        return Optional.ofNullable(currentRoutine);
    }

    public void setRoutineNames(Set<String> routineNames){
        this.routineNames = routineNames;
    }

    public Set<String> getRoutineNames(){
        return routineNames;
    }

    public void render(DrawContext context, MinecraftClient client){
        if (currentRoutine == null) return;
        editorHandler.getCurrentMenu().render(context, client, currentRoutine);
    }

}
