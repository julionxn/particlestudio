package net.pulga22.particlestudio.core.editor;

public enum Modifiers {

    NONE, SHIFT, CONTROL;

    public static Modifiers getModifier(int modifier){
        return switch (modifier){
            case 1 -> Modifiers.SHIFT;
            case 2 -> Modifiers.CONTROL;
            default -> Modifiers.NONE;
        };
    }

}
