package net.pulga22.particlestudio.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccessor {

    @Unique
    private boolean isEditing = false;
    @Unique
    private final PlayerEditor playerEditor = new PlayerEditor();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readNbt(NbtCompound nbt, CallbackInfo ci){
        isEditing = nbt.getBoolean("isEditing");
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeNbt(NbtCompound nbt, CallbackInfo ci){
        nbt.putBoolean("isEditing", isEditing);
    }

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