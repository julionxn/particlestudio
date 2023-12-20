package net.pulga22.particlestudio.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerEntityAccessor {

    @Unique
    private boolean isEditing = false;
    @Unique
    private final PlayerEditor playerEditor = new PlayerEditor((PlayerEntity) (Object) this) ;

    @Override
    public void particlestudio$setEditing(boolean newState) {
        isEditing = newState;
    }

    @Override
    public boolean particlestudio$isEditing() {
        return isEditing;
    }

    @Override
    public PlayerEditor particlestudio$getEditor() {
        return playerEditor;
    }
}