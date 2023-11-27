package net.pulga22.particlestudio.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Nullable public ClientPlayerEntity player;

    @Redirect(method = "handleInputEvents",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V",
                    ordinal = 1))
    public void redirect(MinecraftClient instance, Screen screen){
        if (player == null) return;
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (accessor.particlestudio$isEditing()) return;
        instance.setScreen(screen);
    }

}
