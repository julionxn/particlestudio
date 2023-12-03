package net.pulga22.particlestudio.core.editor.screen.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;

import java.util.function.Consumer;

public class ParticleButtonOption extends ClickableWidget {

    private final SelectedParticleMenu menu;
    private final Identifier normalButtonTexture = new Identifier(ParticleStudio.MOD_ID, "border.png");
    private final Identifier selectedButtonTexture = new Identifier(ParticleStudio.MOD_ID, "active.png");
    private final Consumer<ParticleButtonOption> onClick;
    private final String particleId;

    public ParticleButtonOption(SelectedParticleMenu menu, String particleId, Consumer<ParticleButtonOption> onClick, int x, int y, int width, int height) {
        super(x, y, width, height, Text.of(particleId));
        this.menu = menu;
        this.particleId = particleId;
        this.onClick = onClick;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        this.drawScrollableText(context, client.textRenderer, 8, 0xffffff);
        Identifier texture = menu.getSelectedParticle().equals(particleId) ? selectedButtonTexture : normalButtonTexture;
        context.drawTexture(texture, getX(), getY(), 0, 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        onClick.accept(this);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public static Builder builder(SelectedParticleMenu menu, String particleId, Consumer<ParticleButtonOption> onClick){
        return new Builder(menu, particleId, onClick);
    }

    public static class Builder {

        private final SelectedParticleMenu menu;
        private final String particleId;
        private final Consumer<ParticleButtonOption> onClick;
        private int width = 100;
        private int height = 20;
        private int x = 0;
        private int y = 0;

        public Builder(SelectedParticleMenu menu, String particleId, Consumer<ParticleButtonOption> onClick){
            this.menu = menu;
            this.particleId = particleId;
            this.onClick = onClick;
        }

        public Builder size(int width, int height){
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder position(int x, int y){
            this.x = x;
            this.y = y;
            return this;
        }

        public ParticleButtonOption build(){
            return new ParticleButtonOption(menu, particleId, onClick, x, y, width, height);
        }

    }
}
