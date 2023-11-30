package net.pulga22.particlestudio.core.editor.screen.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;

import java.util.function.BiConsumer;

public class ParticleButtonOption extends ClickableWidget {

    private final BiConsumer<ParticleButtonOption, String> onClick;
    private boolean selected;
    private final String particleId;

    public ParticleButtonOption(String particleId, boolean isActive, BiConsumer<ParticleButtonOption, String> onClick, int x, int y, int width, int height) {
        super(x, y, width, height, Text.of(particleId));
        this.selected = isActive;
        this.particleId = particleId;
        this.onClick = onClick;
    }

    @Override
    protected void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        this.drawScrollableText(context, client.textRenderer, 8, 0xffffff);
        context.drawTexture(new Identifier(ParticleStudio.MOD_ID, "border.png"), getX(), getY(), 0, 0, 0, getWidth(), getHeight(), getWidth(), getHeight());
        if (selected) return;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        super.onClick(mouseX, mouseY);
        onClick.accept(this, particleId);
    }

    public void setSelected(boolean newState){
        selected = newState;
    }


    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    public static Builder builder(String particleId, BiConsumer<ParticleButtonOption, String> onClick){
        return new Builder(particleId, onClick);
    }

    public static class Builder {

        private final String particleId;
        private final BiConsumer<ParticleButtonOption, String> onClick;
        private int width = 100;
        private int height = 20;
        private int x = 0;
        private int y = 0;

        public Builder(String particleId, BiConsumer<ParticleButtonOption, String> onClick){
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
            return new ParticleButtonOption(particleId, false, onClick, x, y, width, height);
        }

    }
}
