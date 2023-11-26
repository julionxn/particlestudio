package net.pulga22.particlestudio.items;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class ParticleController extends Item {

    public ParticleController(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        boolean shouldToggle = !accessor.particlestudio$isEditing();
        if (!world.isClient) {
            if (shouldToggle){
                accessor.particlestudio$setEditing(true);
                ServerPlayNetworking.send((ServerPlayerEntity) player, AllPackets.S2C_TOGGLE_EDITING_MODE, PacketByteBufs.create().writeBoolean(true));
            }
        } else {
            accessor.particlestudio$getEditor().getInputHandler().handleRightClick();
        }
        return super.use(world, player, hand);
    }

}
