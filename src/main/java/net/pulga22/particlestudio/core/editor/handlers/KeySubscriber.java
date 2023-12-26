package net.pulga22.particlestudio.core.editor.handlers;

import net.pulga22.particlestudio.core.routines.Routine;

public interface KeySubscriber {
    void onKey(int key, Modifiers modifier, Routine routine);
}
