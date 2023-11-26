package net.pulga22.particlestudio.core.editor.screen;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.core.routines.WorldRoutines;
import net.pulga22.particlestudio.utils.mixins.PlayerEntityAccessor;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class RoutineSelectionMenu extends Screen {

    private final PlayerEntity player;
    private final PlayerEditor playerEditor;
    private final WorldRoutines worldRoutines;

    public RoutineSelectionMenu(PlayerEntity player) {
        super(Text.of("RoutineSelectionMenu"));
        this.player = player;
        this.playerEditor = ((PlayerEntityAccessor) player).particlestudio$getEditor();
        this.worldRoutines = playerEditor.getLoadedRoutines();
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        int routinesSize = worldRoutines.routines.size();
        int startingX = client.getWindow().getScaledWidth() / 2 - 75;
        AtomicInteger startingY = new AtomicInteger((int) (client.getWindow().getScaledHeight() / 2 - (Math.floor((routinesSize / 2) * 30))));
        this.addRoutineButton("Nueva rutina", editor -> {
            System.out.println("NUEVA RUTINA");
        }, startingX, startingY.get());
        startingY.addAndGet(30);
        worldRoutines.routines.forEach((name, routine) -> {
            addRoutineButton(name, editor -> {}, startingX, startingY.getAndAdd(30));
        });
    }

    private void addRoutineButton(String title, Consumer<PlayerEditor> onPress, int x, int y){
        ButtonWidget routineButton = ButtonWidget.builder(Text.of(title), button -> onPress.accept(playerEditor))
                .dimensions(x, y, 150, 20)
                .build();
    }


}
