package net.pulga22.particlestudio.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.handlers.Modifiers;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(Keyboard.class)
public abstract class KeyboardMixin {

    @Shadow @Final private MinecraftClient client;
    @Unique
    private final Set<Integer> keysToHandle = Set.of(256, 81, 69, 90, 67, 75, 258, 340, 341);

    @Inject(method = "onKey", at = @At("TAIL"))
    public void handleInput(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci){
        if (!keysToHandle.contains(key)) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        EditorHandler handler = accessor.particlestudio$getEditor().getHandler();
        switch (action){
            case 0 -> handler.setCurrentPhase(Modifiers.NONE);
            case 1, 2 -> {
                switch (key){
                    case 340 -> handler.setCurrentPhase(Modifiers.SHIFT);
                    case 341 -> handler.setCurrentPhase(Modifiers.CTRL);
                }
            }
        }
        if (action != 1) return;
        handler.onKey(key);
    }

}
