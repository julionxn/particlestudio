package net.pulga22.particlestudio.mixin;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashSet;
import java.util.Set;

@Mixin(Keyboard.class)
public class KeyboardMixin {

    @Shadow @Final private MinecraftClient client;
    @Unique
    private final Set<Integer> keysToHandle = new HashSet<>(){{
       add(256); add(81); add(69); add(90); add(67);
    }};

    @Inject(method = "onKey", at = @At("TAIL"))
    public void handleInput(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci){
        PlayerEntity player = client.player;
        if (player == null) return;
        if (!keysToHandle.contains(key)) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        accessor.particlestudio$getEditor().getInputHandler().handleKeyboard(key);
    }

}
