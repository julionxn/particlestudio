package net.pulga22.particlestudio.core.editor;

import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.core.routines.WorldRoutines;

import java.util.Optional;

public class PlayerEditor {

    private final EditorInputHandler editorInputHandler;
    private WorldRoutines loadedRoutines;
    private Routine currentRoutine = null;

    public PlayerEditor(){
        this.editorInputHandler = new EditorInputHandler(this);
    }

    public EditorInputHandler getInputHandler(){
        return editorInputHandler;
    }

    public void loadRoutines(WorldRoutines worldRoutines){
        loadedRoutines = worldRoutines;
    }

    public WorldRoutines getLoadedRoutines(){
        return loadedRoutines;
    }

    public void setActiveRoutine(Routine routine){
        currentRoutine = routine;
    }

    public Optional<Routine> getCurrentRoutine(){
        return Optional.ofNullable(currentRoutine);
    }

}
