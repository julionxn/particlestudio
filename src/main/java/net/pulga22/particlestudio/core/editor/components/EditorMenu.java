package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.handlers.KeySubscriber;
import net.pulga22.particlestudio.core.editor.handlers.Modifiers;
import net.pulga22.particlestudio.core.editor.handlers.ScrollSubscriber;
import net.pulga22.particlestudio.core.routines.Routine;
import net.pulga22.particlestudio.core.routines.Timeline;
import net.pulga22.particlestudio.utils.Keys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditorMenu implements KeySubscriber, ScrollSubscriber {

    private static final Identifier ACTIVE_BUTTON_TEXTURE = of("active.png");
    private static final Identifier BORDER_BUTTON_TEXTURE = of("border.png");
    private static final Map<Actions, Identifier> OPTIONS_BORDER_TEXTURES = Map.of(
            Actions.Q, of("q_option.png"),
            Actions.E, of("e_option.png"),
            Actions.Z, of("z_option.png"),
            Actions.C, of("c_option.png")
    );
    private final List<EditorButton> buttons = new ArrayList<>();
    private int currentIndex = 0;
    protected final EditorHandler editorHandler;
    private final String menuName;

    public EditorMenu(EditorHandler editorHandler, String menuName){
        this.editorHandler = editorHandler;
        this.menuName = menuName;
    }

    public void render(DrawContext context, MinecraftClient client, Routine currentRoutine){
        context.drawTextWithShadow(client.textRenderer, menuName, 6, 6, 0xffffff);
        renderTickInfo(context, client, currentRoutine);
        renderButtons(context, client);
        renderActiveOptions(context, client);
    }

    private void renderTickInfo(DrawContext context, MinecraftClient client, Routine currentRoutine){
        Timeline timeline = currentRoutine.getTimeline();
        int length = timeline.displayLength();
        int currentEditingTick = timeline.getCurrentEditingTick();
        context.drawCenteredTextWithShadow(client.textRenderer,
                "Tick: " + currentEditingTick + "/" + length + " (" + tickToSec(currentEditingTick) + "s:" + tickToSec(length) + "s)",
                client.getWindow().getScaledWidth() / 2,
                client.getWindow().getScaledHeight() - 42,
                0xffffff);
        context.drawCenteredTextWithShadow(client.textRenderer,
                "Seeing from " + timeline.onionLowerBound() + " to " + timeline.onionUpperBound(),
                client.getWindow().getScaledWidth() / 2,
                client.getWindow().getScaledHeight() - 32,
                0xffffff);
    }

    private String tickToSec(int tick){
        float secs = tick / 20f;
        return String.format("%.2f", secs);
    }

    private void renderButtons(DrawContext context, MinecraftClient client){
        int buttonsAmount = buttons.size();
        int x = context.getScaledWindowWidth() / 2 - ((15 * buttonsAmount) - 5);
        for (int i = 0; i < buttonsAmount; i++) {
            EditorButton currentButton = buttons.get(i);
            context.drawTexture(currentButton.getButtonTexture(), x, 10, 0, 0, 0, 20, 20, 20, 20);
            if (i == currentIndex){
                context.drawCenteredTextWithShadow(client.textRenderer, currentButton.getDescription(), context.getScaledWindowWidth() / 2, 36, 0xffffff);
                context.drawTexture(ACTIVE_BUTTON_TEXTURE, x - 2, 8, 0, 0, 0, 24, 24, 24, 24);
            } else {
                context.drawTexture(BORDER_BUTTON_TEXTURE, x - 2, 8, 0, 0, 0, 24, 24, 24, 24);
            }
            x += 30;
        }
    }

    private void renderActiveOptions(DrawContext context, MinecraftClient client){
        EditorButton activeButton = buttons.get(currentIndex);
        List<EditorButtonAction> parts = activeButton.getActions();
        int y = context.getScaledWindowHeight() / 2 - ((15 * parts.size()) - 5);
        for (EditorButtonAction part : parts) {
            Modifiers currentPhase = editorHandler.getCurrentPhase();
            context.drawTexture(part.getTexture(currentPhase), 10, y, 0, 0, 0, 20, 20, 20, 20);
            context.drawTexture(OPTIONS_BORDER_TEXTURES.get(part.getAction()), 8, y - 2, 0, 0, 0, 28, 28, 28, 28);
            context.drawTextWithShadow(client.textRenderer, part.getDescription(currentPhase), 38, y + 5, 0xffffff);
            y += 30;
        }
    }

    public void onKey(int key, Modifiers modifier){
        EditorButton currentButton = buttons.get(currentIndex);
        switch (key){
            case Keys.TAB -> onScroll(1.0);
            case Keys.Q -> currentButton.perform(Actions.Q, modifier, editorHandler.getRoutine());
            case Keys.E -> currentButton.perform(Actions.E, modifier, editorHandler.getRoutine());
            case Keys.Z -> currentButton.perform(Actions.Z, modifier, editorHandler.getRoutine());
            case Keys.C -> currentButton.perform(Actions.C, modifier, editorHandler.getRoutine());
        }
    }

    public void onScroll(double vertical){
        if (vertical > 0) {
            currentIndex = (currentIndex < buttons.size() - 1) ? currentIndex + 1 : 0;
        } else if (vertical < 0) {
            currentIndex = (currentIndex > 0) ? currentIndex - 1 : buttons.size() - 1;
        }
    }

    protected void addButton(EditorButton button){
        this.buttons.add(button);
    }

    protected static Identifier of(String path){
        return new Identifier(ParticleStudio.MOD_ID, "buttons/masks/" + path);
    }

    public void onExit(Routine routine){

    }

    public void onChange(Routine routine){
        editorHandler.unsubscribeToKey(this);
        editorHandler.unsubscribeToScroll(this);
    }

    public void onActive(Routine routine){
        editorHandler.subscribeToScroll(this);
        editorHandler.subscribeToKey(this);
    }

}
