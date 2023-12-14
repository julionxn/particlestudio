package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.handlers.Modifiers;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.function.Consumer;

public class EditorButtonAction {

    private final Actions action;
    private final EditorButtonActionPhase normalPhase;
    private final EditorButtonActionPhase shiftPhase;
    private final EditorButtonActionPhase ctrlPhase;

    public EditorButtonAction(Actions action, EditorButtonActionPhase normalPhase, EditorButtonActionPhase shiftPhase, EditorButtonActionPhase ctrlPhase){
        this.action = action;
        this.normalPhase = normalPhase;
        this.shiftPhase = shiftPhase;
        this.ctrlPhase = ctrlPhase;
    }

    public void normal(Routine routine){
        this.normalPhase.perform(routine);
    }

    public void shift(Routine routine){
        this.shiftPhase.perform(routine);
    }

    public void ctrl(Routine routine){
        this.ctrlPhase.perform(routine);
    }

    public Identifier getTexture(Modifiers modifier){
        return switch (modifier){
            case SHIFT -> shiftPhase.getTexture();
            case CTRL -> ctrlPhase.getTexture();
            default -> normalPhase.getTexture();
        };
    }

    public String getDescription(Modifiers modifier){
        return switch (modifier){
            case SHIFT -> shiftPhase.getDescription();
            case CTRL -> ctrlPhase.getDescription();
            default -> normalPhase.getDescription();
        };
    }

    public Actions getAction(){
        return action;
    }

    public static class Builder{

        private final String path;
        private final Actions action;
        private final EditorButtonActionPhase normalPhase;
        private EditorButtonActionPhase shiftPhase;
        private EditorButtonActionPhase ctrlPhase;

        public Builder(String path, Actions action, Identifier normalTexture, Consumer<Routine> normalAction, String description){
            this.path = path;
            this.action = action;
            this.normalPhase = new EditorButtonActionPhase(normalTexture, normalAction, description);
        }

        public Builder setShiftAction(Consumer<Routine> shiftAction, String description){
            shiftPhase = new EditorButtonActionPhase(getTextureOf(Modifiers.SHIFT), shiftAction, description);
            return this;
        }

        public Builder setCtrlAction(Consumer<Routine> ctrlAction, String description){
            ctrlPhase = new EditorButtonActionPhase(getTextureOf(Modifiers.CTRL), ctrlAction, description);
            return this;
        }

        public EditorButtonAction build(){
            EditorButtonActionPhase shiftP = shiftPhase == null ? normalPhase : shiftPhase;
            EditorButtonActionPhase ctrlP = ctrlPhase == null ? normalPhase : ctrlPhase;
            return new EditorButtonAction(action, normalPhase, shiftP, ctrlP);
        }

        private Identifier getTextureOf(Modifiers modifiers){
            return new Identifier(ParticleStudio.MOD_ID, "buttons/" + path + "/" + action.toString().toLowerCase() + modifiers.toString().toLowerCase() + ".png");
        }

    }
}
