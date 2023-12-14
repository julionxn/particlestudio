package net.pulga22.particlestudio.core.editor.handlers;

public enum Modifiers {

    NONE, SHIFT, CTRL;

    public static Modifiers getModifier(int modifier){
        return switch (modifier){
            case 1 -> Modifiers.SHIFT;
            case 2 -> Modifiers.CTRL;
            default -> Modifiers.NONE;
        };
    }

}
