package net.pulga22.particlestudio.core.editor.screen.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.pulga22.particlestudio.core.editor.PlayerEditor;
import net.pulga22.particlestudio.core.routines.Routine;

public class NewRoutineMenu extends Screen {

    private final Screen previousScreen;
    private final PlayerEditor playerEditor;
    private final int hashWorld;

    public NewRoutineMenu(Screen previousScreen, PlayerEditor playerEditor, int hashWorld) {
        super(Text.of("NewRoutine"));
        this.previousScreen = previousScreen;
        this.playerEditor = playerEditor;
        this.hashWorld = hashWorld;
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        int centerX = client.getWindow().getScaledWidth() / 2;
        int centerY = client.getWindow().getScaledHeight() / 2;
        TextFieldWidget name = new TextFieldWidget(client.textRenderer,
                centerX - 50,
                centerY - 40,
                100, 20, Text.of("Nombre"));
        ButtonWidget createButton = ButtonWidget.builder(Text.of("Nueva"), button -> {
            String nameText = name.getText();
            if (playerEditor.getRoutineNames().contains(nameText)) return;
            Routine routine = playerEditor.createRoutine(nameText, hashWorld);
            if (routine == null) return;
            playerEditor.setActiveRoutine(routine);
            playerEditor.openEditor();
            close();
        }).dimensions(centerX - 100, centerY - 10, 80, 20).build();
        ButtonWidget cancelButton = ButtonWidget.builder(Text.of("Cancelar"), button -> client.setScreen(previousScreen)).dimensions(centerX + 20, centerY - 10, 80, 20).build();
        addDrawableChild(createButton);
        addDrawableChild(cancelButton);
        addDrawableChild(name);
    }

}
