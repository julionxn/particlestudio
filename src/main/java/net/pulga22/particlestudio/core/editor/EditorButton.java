package net.pulga22.particlestudio.core.editor;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;

public class EditorButton {

    private final Identifier buttonTexture;
    private final HashMap<Actions, EditorButtonPart> actions = new HashMap<>();

    public EditorButton(Identifier buttonTexture){
        this.buttonTexture = buttonTexture;
    }

    public EditorButton setAction(Actions action, Identifier texture, Consumer<Routine> consumer, String description){
        actions.put(action, new EditorButtonPart(texture, consumer, description));
        return this;
    }

    public void perform(Actions action, Routine routine){
        if (!actions.containsKey(action)) return;
        actions.get(action).perform(routine);
    }

    public Optional<Identifier> getTexture(Actions action){
        if (!actions.containsKey(action)) return Optional.empty();
        return Optional.of(actions.get(action).getTexture());
    }

    public Optional<String> getDescription(Actions action){
        if (!actions.containsKey(action)) return Optional.empty();
        return Optional.of(actions.get(action).getDescription());
    }

    public Identifier getButtonTexture(){
        return buttonTexture;
    }

    public static Builder builder(String path){
        return new Builder(path);
    }

    public static class Builder {

        private final String path;
        private final EditorButton button;

        public Builder(String path){
            this.path = path;
            button = new EditorButton(new Identifier(ParticleStudio.MOD_ID, "textures/buttons/" + path + "main.png"));
        }

        public Builder setAction(Actions action, Consumer<Routine> consumer, String description){
            button.setAction(action, getTextureOf(action), consumer, description);
            return this;
        }

        private Identifier getTextureOf(Actions action){
            return new Identifier(ParticleStudio.MOD_ID, "textures/buttons/" + path + action.toString().toLowerCase() + ".png");
        }

        public EditorButton build(){
            return button;
        }

    }

}
