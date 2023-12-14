package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.handlers.Modifiers;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class EditorButton {

    private final Identifier buttonTexture;
    private final String description;
    private final HashMap<Actions, EditorButtonAction> actions = new HashMap<>();
    private Function<Routine, Boolean> predicate = routine -> true;

    public EditorButton(Identifier buttonTexture, String description){
        this.buttonTexture = buttonTexture;
        this.description = description;
    }

    public void setAction(Actions action, EditorButtonAction buttonAction){
        actions.put(action, buttonAction);
    }

    public void perform(Actions action, Modifiers modifier, Routine routine){
        if (!actions.containsKey(action)) return;
        EditorButtonAction buttonAction = actions.get(action);
        switch (modifier){
            case SHIFT -> buttonAction.shift(routine);
            case CTRL -> buttonAction.ctrl(routine);
            default -> buttonAction.normal(routine);
        }
    }

    public List<EditorButtonAction> getActions(){
        List<EditorButtonAction> buttonParts = new ArrayList<>();
        for (Actions action : Actions.values()){
            if (actions.containsKey(action)) buttonParts.add(actions.get(action));
        }
        return buttonParts;
    }

    public void setPredicate(Function<Routine, Boolean> predicate){
        this.predicate = predicate;
    }

    public Function<Routine, Boolean> getPredicate(){
        return predicate;
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
            button = new EditorButton(new Identifier(ParticleStudio.MOD_ID, "buttons/" + path + "/main.png"), description);
        }

        public Builder setAction(Actions action, Consumer<Routine> normalAction, String description, Function<EditorButtonAction.Builder, EditorButtonAction.Builder> builderFunction){
            EditorButtonAction.Builder builder = builderFunction.apply(new EditorButtonAction.Builder(path, action, getTextureOf(action), normalAction, description));
            button.setAction(action, builder.build());
            return this;
        }

        public Builder setPredicate(Function<Routine, Boolean> predicate){
            button.setPredicate(predicate);
            return this;
        }

        private Identifier getTextureOf(Actions action){
            return new Identifier(ParticleStudio.MOD_ID, "buttons/" + path + "/" + action.toString().toLowerCase() + ".png");
        }

        public EditorButton build(){
            return button;
        }

    }

}
