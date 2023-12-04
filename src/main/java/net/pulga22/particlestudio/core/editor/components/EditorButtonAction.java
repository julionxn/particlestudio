package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.function.Consumer;

public class EditorButtonAction {

    private final Actions action;
    private final Consumer<Routine> consumer;
    private final Identifier texture;
    private final String description;

    public EditorButtonAction(Actions action, Identifier texture, Consumer<Routine> consumer, String description){
        this.action = action;
        this.consumer = consumer;
        this.texture = texture;
        this.description = description;
    }

    public void perform(Routine routine){
        this.consumer.accept(routine);
    }

    public Identifier getTexture(){
        return texture;
    }

    public String getDescription(){
        return description;
    }

    public Actions getAction(){
        return action;
    }

}
