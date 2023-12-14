package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.function.Consumer;

public class EditorButtonActionPhase {

    private final String description;
    private final Identifier texture;
    private final Consumer<Routine> action;

    public EditorButtonActionPhase(Identifier texture, Consumer<Routine> action, String description) {
        this.description = description;
        this.texture = texture;
        this.action = action;
    }

    public String getDescription(){
        return description;
    }

    public Identifier getTexture(){
        return texture;
    }

    public void perform(Routine routine){
        action.accept(routine);
    }
}
