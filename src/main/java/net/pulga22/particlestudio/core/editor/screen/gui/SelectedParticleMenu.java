package net.pulga22.particlestudio.core.editor.screen.gui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.handlers.ScrollSubscriber;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;

import java.util.List;

public class SelectedParticleMenu extends Screen implements ScrollSubscriber {

    private final EditorHandler editorHandler;
    private final List<String> allParticlesIds;
    private final int particlesButtons;
    private int startingParticleIndex = 0;
    private int buttonsToDisplay = 1;

    public SelectedParticleMenu(EditorHandler editorHandler) {
        super(Text.of("SelectedParticleMenu"));
        this.editorHandler = editorHandler;
        editorHandler.subscribeToScroll(this);
        allParticlesIds = ParticleRoutinesManager.getInstance().getAllParticleIds();
        particlesButtons = ParticleRoutinesManager.getInstance().getParticlesAmount();
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        Window window = client.getWindow();
        buttonsToDisplay = window.getScaledHeight() / 35;
        int buttonSectionHeight = buttonsToDisplay * 10;
        int y = window.getScaledHeight() / 2 - (buttonSectionHeight / 2);
        int x = (window.getScaledWidth() / 2) - 90;
        if (startingParticleIndex + buttonsToDisplay > particlesButtons){
            startingParticleIndex = particlesButtons - buttonsToDisplay;
        }

        TextFieldWidget textFieldWidget = new TextFieldWidget(client.textRenderer, x, y - 23, 120, 20, Text.of("TEST"));
        textFieldWidget.setMaxLength(100);
        addDrawableChild(textFieldWidget);

        ButtonWidget search = ButtonWidget.builder(Text.of("Set"), button -> {
            String selected = textFieldWidget.getText();
            ParticleRoutinesManager.getInstance().getParticleType(selected).ifPresent(particleType -> editorHandler.changeCurrentParticle(selected));
        }).dimensions(x + 121, y - 23, 60, 20).build();
        addDrawableChild(search);

        ButtonWidget upButton = ButtonWidget.builder(Text.of("⮝"), button -> {
            adjustParticleIndex(-1);
            clearAndInit();
        }).dimensions(x + 180, y - 1, 15, 15).build();
        addDrawableChild(upButton);
        for (int i = startingParticleIndex; i - startingParticleIndex < buttonsToDisplay; i++){
            String id = allParticlesIds.get(i);
            ParticleButtonOption buttonOption = ParticleButtonOption.builder(this, id, button -> editorHandler.changeCurrentParticle(id)).position(x, y).size(180, 20).build();
            addDrawableChild(buttonOption);
            y += 20;
        }
        ButtonWidget downButton = ButtonWidget.builder(Text.of("⮟"), button -> {
            adjustParticleIndex(1);
            clearAndInit();
        }).dimensions(x + 180, y - 14, 15, 15).build();
        addDrawableChild(downButton);

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        int x = context.getScaledWindowWidth() / 2;
        int y = context.getScaledWindowHeight() / 2;
        int buttonSectionHeight = buttonsToDisplay * 10;
        int totalHeight = buttonSectionHeight * 2 - 30;
        int barHeight = (buttonsToDisplay * (particlesButtons - 1)) / totalHeight;
        int barY = (int) ((totalHeight - barHeight) * ((float) startingParticleIndex / (particlesButtons - buttonsToDisplay)));
        context.drawTexture(new Identifier(ParticleStudio.MOD_ID, "icon.png"),
                x + 90,
                (y - buttonSectionHeight / 2 ) + 15 + barY,
                0, 0, 0,
                15, barHeight, 15, barHeight
                );
        if (client == null) return;
        context.drawCenteredTextWithShadow(client.textRenderer,
                "Actual: " + editorHandler.getCurrentParticle(),
                x, y - (buttonsToDisplay * 5) - 50, 0xffffff);
    }

    private void adjustParticleIndex(int amount){
        if (startingParticleIndex + amount < 0) return;
        if (startingParticleIndex + amount + buttonsToDisplay > particlesButtons) return;
        startingParticleIndex += amount;
    }

    public String getSelectedParticle(){
        return editorHandler.getCurrentParticle();
    }

    @Override
    public void close() {
        super.close();
        editorHandler.setScrollActive(true);
        editorHandler.unsubscribeToScroll(this);
    }

    @Override
    public void onScroll(double vertical) {
        adjustParticleIndex((int) -vertical);
        clearAndInit();
    }
}
