package net.pulga22.particlestudio.core.editor.screen.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.particle.ParticleType;
import net.minecraft.text.Text;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.routines.ParticleRoutinesManager;

import java.util.List;

public class SelectedParticleMenu extends Screen {

    private final EditorHandler handler;
    private final List<String> allParticlesIds;
    private final int particlesButtons;
    private int startingParticleIndex = 0;
    private final int buttonsToDisplay = 5;
    private ParticleButtonOption selectedButton;

    public SelectedParticleMenu(EditorHandler handler) {
        super(Text.of("SelectedParticleMenu"));
        this.handler = handler;
        handler.subscribeToScroll(this);
        allParticlesIds = ParticleRoutinesManager.getInstance().getAllParticleIds();
        particlesButtons = ParticleRoutinesManager.getInstance().getParticlesAmount();
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        Window window = client.getWindow();
        int y = (window.getScaledHeight() / 2) - (buttonsToDisplay * 10);
        int x = (window.getScaledWidth() / 2) - 90;
        for (int i = startingParticleIndex; i - startingParticleIndex < buttonsToDisplay; i++){
            String id = allParticlesIds.get(i);
            ParticleButtonOption buttonOption = ParticleButtonOption.builder(id, button -> {
                handler.changeSelectedParticle(id);
                if (selectedButton != null) selectedButton.setSelected(false);
                button.setSelected(true);
            }).position(x, y).size(180, 20).build();
            if (handler.getSelectedParticle().equals(id)) {
                buttonOption.setSelected(true);
                selectedButton = buttonOption;
            };
            addDrawableChild(buttonOption);
            y += 20;
        }
    }

    public void handleMouseScroll(double vertical){

    }

    @Override
    public void close() {
        super.close();
        handler.unsubscribeToScroll(this);
    }

}
