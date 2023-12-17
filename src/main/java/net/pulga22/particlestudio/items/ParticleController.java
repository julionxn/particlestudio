package net.pulga22.particlestudio.items;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

public class ParticleController extends Item {

    public ParticleController(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        PlayerEntityAccessor accessor = (PlayerEntityAccessor) player;
        if (world.isClient){
            if (player.isSneaking()){
                openMainMenu(player);
            } else {
                PlayerEditor editor = accessor.particlestudio$getEditor();
                editor.getCurrentRoutine().ifPresentOrElse(routine -> accessor.particlestudio$getEditor().openEditor(), () -> openMainMenu(player));
            }
        }
        return super.use(world, player, hand);
    }

    private void openMainMenu(PlayerEntity player){
        ClientPlayNetworking.send(AllPackets.C2S_REQUEST_PARTICLE_CONTROLLER_SCREEN, PacketByteBufs.empty());
    }

}
