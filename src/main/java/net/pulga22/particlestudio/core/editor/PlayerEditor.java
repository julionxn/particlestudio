package net.pulga22.particlestudio.core.editor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PlayerEditor {

    private final EditorHandler editorHandler;
    private final HashMap<String, Routine> loadedRoutines = new HashMap<>();
    private Set<String> routineNames = new HashSet<>();
    private Routine currentRoutine = null;

    public PlayerEditor(PlayerEntity player){
        this.editorHandler = new EditorHandler(this, player);
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

    public void addNewRoutine(String name){
        if (routineNames.contains(name)) return;
        loadRoutine(name, new Routine());
        routineNames.add(name);
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
