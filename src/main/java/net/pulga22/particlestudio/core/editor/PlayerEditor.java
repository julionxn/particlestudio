package net.pulga22.particlestudio.core.editor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.core.routines.managers.PartialRoutine;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.*;

public class PlayerEditor {

    private final HashMap<UUID, Routine> loadedRoutines = new HashMap<>();
    private final HashMap<UUID, PartialRoutine> routinesToLoad = new HashMap<>();
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

    public void loadRoutine(Routine routine){
        this.loadedRoutines.put(routine.uuid, routine);
    }

    public Optional<Routine> getRoutine(UUID uuid){
        return Optional.ofNullable(loadedRoutines.get(uuid));
    }

    public void setActiveRoutine(Routine routine){
        currentRoutine = routine;
    }

    public Routine createRoutine(String name, int hashWorld){
        if (routineNames.contains(name)) return null;
        routineNames.add(name);
        Routine routine = new Routine(UUID.randomUUID(), name, hashWorld);
        loadRoutine(routine);
        return routine;
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

    public void prepareToLoad(UUID uuid){
        routinesToLoad.put(uuid, new PartialRoutine(uuid));
    }

    public void loadChunk(int index, int end, UUID uuid, byte[] data){
        PartialRoutine routineToLoad = routinesToLoad.get(uuid);
        routineToLoad.appendBytes(index, data);
        if (end == routineToLoad.getSize()){
            routineToLoad.getRoutine().ifPresentOrElse(routine -> {
                ParticleStudio.LOGGER.info("Routine " + routine.name + " loaded.");
               loadRoutine(routine);
               setActiveRoutine(routine);
               openEditor();
            }, () -> ParticleStudio.LOGGER.error("Something went wrong saving routine with UUID " + uuid));
            routinesToLoad.remove(uuid);
        }
    }

}
