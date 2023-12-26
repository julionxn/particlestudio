package net.pulga22.particlestudio.core.editor.handlers;

import net.pulga22.particlestudio.core.routines.Routine;

public interface ScrollSubscriber {
    void onScroll(double vertical, Routine routine);
}
