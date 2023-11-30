package net.pulga22.particlestudio.core.editor.screen.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.pulga22.particlestudio.core.editor.EditorHandler;

public class SelectedParticleMenu extends Screen {

    private final EditorHandler handler;

    public SelectedParticleMenu(EditorHandler handler) {
        super(Text.of("SelectedParticleMenu"));
        this.handler = handler;
        handler.subscribeToScroll(this);
    }

    @Override
    protected void init() {
        super.init();
        if (client == null) return;
        Window window = client.getWindow();
        ParticleButtonOption buttonOption = ParticleButtonOption.builder("minecraft:happy_villagerxddddddddddddddd", (particleButtonOption, string) -> {
            System.out.println("HOLA");
        }).size(180, 20).position(window.getScaledWidth() / 2 , window.getScaledHeight() / 2).build();
        addDrawableChild(buttonOption);
    }

    public void handleMouseScroll(double vertical){

    }

    @Override
    public void close() {
        super.close();
        handler.unsubscribeToScroll(this);
    }

}
