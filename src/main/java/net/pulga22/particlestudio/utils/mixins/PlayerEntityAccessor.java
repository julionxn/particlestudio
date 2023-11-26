package net.pulga22.particlestudio.utils.mixins;

import net.pulga22.particlestudio.core.editor.PlayerEditor;

public interface PlayerEntityAccessor {
    void particlestudio$setEditing(boolean newState);
    boolean particlestudio$isEditing();
    PlayerEditor particlestudio$getEditor();
}
