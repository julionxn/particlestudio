package net.pulga22.particlestudio.items;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.core.editor.screen.RoutineSelectionMenu;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class ParticleController extends Item {

    public ParticleController(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (world.isClient && !accessor.particlestudio$isEditing()){
            if (player.isSneaking()){
                openMainMenu(player);
            } else {
                PlayerEditor editor = accessor.particlestudio$getEditor();
                editor.getCurrentRoutine().ifPresentOrElse(routine -> {
                    accessor.particlestudio$setEditing(true);
                }, () -> openMainMenu(player));
            }
        }
        return super.use(world, player, hand);
    }

    private void openMainMenu(PlayerEntity player){
        MinecraftClient.getInstance().setScreen(new RoutineSelectionMenu(player));
    }

}
