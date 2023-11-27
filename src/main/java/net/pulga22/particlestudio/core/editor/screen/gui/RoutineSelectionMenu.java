package net.pulga22.particlestudio.core.editor.screen.gui;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.Set;
import java.util.function.Consumer;

public class RoutineSelectionMenu extends Screen {

    private final PlayerEntity player;
    private final PlayerEditor playerEditor;
    private final Set<String> routineNames;

    public RoutineSelectionMenu(PlayerEntity player) {
        super(Text.of("RoutineSelectionMenu"));
        this.player = player;
        this.playerEditor = ((PlayerEntityAccessor) player).particlestudio$getEditor();
        this.routineNames = playerEditor.getRoutineNames();
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        int routinesSize = routineNames.size() + 1;
        int x = client.getWindow().getScaledWidth() / 2 - 75;
        int y = (int) (client.getWindow().getScaledHeight() / 2 - 15 - Math.ceil((routinesSize / 2) * 30));
        addRoutineButton("Nueva rutina", editor -> client.setScreen(new NewRoutineMenu(this, editor)), x, y);
        y += 30;
        for (String name : routineNames) {
            addRoutineButton(name, editor -> {
                ClientPlayNetworking.send(AllPackets.C2S_REQUEST_ROUTINE_SYNC, PacketByteBufs.create().writeString(name));
                close();
            }, x, y);
            y += 30;
        }
        
    }

    private void addRoutineButton(String title, Consumer<PlayerEditor> onPress, int x, int y){
        ButtonWidget routineButton = ButtonWidget.builder(Text.of(title), button -> onPress.accept(playerEditor))
                .dimensions(x, y, 150, 20)
                .build();
        addDrawableChild(routineButton);
    }


}
