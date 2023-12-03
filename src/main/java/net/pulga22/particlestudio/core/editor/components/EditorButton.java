package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class EditorButton {

    private final Identifier buttonTexture;
    private final String description;
    private final HashMap<Actions, EditorButtonPart> actions = new HashMap<>();

    public EditorButton(Identifier buttonTexture, String description){
        this.buttonTexture = buttonTexture;
        this.description = description;
    }

    public void setAction(Actions action, Identifier texture, Consumer<Routine> consumer, String description){
        actions.put(action, new EditorButtonPart(action, texture, consumer, description));
    }

    public void perform(Actions action, Routine routine){
        if (!actions.containsKey(action)) return;
        actions.get(action).perform(routine);
    }

    public List<EditorButtonPart> getActions(){
        List<EditorButtonPart> buttonParts = new ArrayList<>();
        for (Actions action : Actions.values()){
            if (actions.containsKey(action)) buttonParts.add(actions.get(action));
        }
        return buttonParts;
    }

    public Identifier getButtonTexture(){
        return buttonTexture;
    }

    public String getDescription() {
        return description;
    }

    public static Builder builder(String path, String description){
        return new Builder(path, description);
    }

    public static class Builder {

        private final String path;
        private final EditorButton button;

        public Builder(String path, String description){
            this.path = path;
            button = new EditorButton(new Identifier(ParticleStudio.MOD_ID, "textures/buttons/" + path + "/main.png"), description);
        }

        public Builder setAction(Actions action, Consumer<Routine> consumer, String description){
            button.setAction(action, getTextureOf(action), consumer, description);
            return this;
        }

        private Identifier getTextureOf(Actions action){
            return new Identifier(ParticleStudio.MOD_ID, "textures/buttons/" + path + "/" + action.toString().toLowerCase() + ".png");
        }

        public EditorButton build(){
            return button;
        }

    }

}
