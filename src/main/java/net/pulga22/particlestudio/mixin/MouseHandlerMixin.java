package net.pulga22.particlestudio.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.entity.player.PlayerEntity;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mouse.class)
public class MouseHandlerMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onMouseScroll", at = @At("HEAD"), cancellable = true)
    public void handleScroll(long window, double horizontal, double vertical, CallbackInfo ci){
        PlayerEntity player = client.player;
        if (player == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        if (window == client.getWindow().getHandle()){
            accessor.particlestudio$getEditor().getInputHandler().handleMouseScroll(vertical);
        }
        ci.cancel();
    }

    @Inject(method = "onMouseButton", at = @At("HEAD"))
    public void handleClick(long window, int button, int action, int mods, CallbackInfo ci){
        if (button != 1) return;
        PlayerEntity player = client.player;
        if (player == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (!accessor.particlestudio$isEditing()) return;
        accessor.particlestudio$getEditor().getInputHandler().handleRightClick();
    }

}
