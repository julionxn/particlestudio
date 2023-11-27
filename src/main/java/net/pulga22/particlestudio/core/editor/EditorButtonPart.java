package net.pulga22.particlestudio.core.editor;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.function.Consumer;

public class EditorButtonPart {

    private final Consumer<Routine> action;
    private final Identifier texture;
    private final String description;

    public EditorButtonPart(Identifier texture, Consumer<Routine> action, String description){
        this.action = action;
        this.texture = texture;
        this.description = description;
    }

    public void perform(Routine routine){
        this.action.accept(routine);
    }

    public Identifier getTexture(){
        return texture;
    }

    public String getDescription(){
        return description;
    }

}
