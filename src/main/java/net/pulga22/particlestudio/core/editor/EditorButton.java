package net.pulga22.particlestudio.core.editor;

import net.pulga22.particlestudio.core.routines.Routine;

public interface EditorButton {
    void onQPressed(Routine routine);
    void onEPressed(Routine routine);
    void onZPressed(Routine routine);
    void onCPressed(Routine routine);
    void onRightClick(Routine routine);
}
