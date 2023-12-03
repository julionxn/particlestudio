package net.pulga22.particlestudio.core.editor.components;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorHandler;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EditorMenu {

    private static final Identifier ACTIVE_BUTTON_TEXTURE = of("active.png");
    private static final Identifier BORDER_BUTTON_TEXTURE = of("border.png");
    private static final HashMap<Actions, Identifier> OPTIONS_BORDER_TEXTURES = new HashMap<>(){{
       put(Actions.Q, of("q_option.png"));
       put(Actions.E, of("e_option.png"));
       put(Actions.Z, of("z_option.png"));
       put(Actions.C, of("c_option.png"));
    }};
    private final List<EditorButton> buttons = new ArrayList<>();
    private int currentIndex = 0;
    private final EditorMenu previousMenu;
    private final EditorHandler editorHandler;
    private final String menuName;

    public EditorMenu(EditorMenu previousMenu, EditorHandler editorHandler, String menuName){
        this.previousMenu = previousMenu;
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
        int length = currentRoutine.displayLength();
        int currentEditingTick = currentRoutine.getCurrentEditingTick();
        context.drawCenteredTextWithShadow(client.textRenderer,
                "Tick: " + currentEditingTick + "/" + length + " (" + tickToSec(currentEditingTick) + "s:" + tickToSec(length) + "s)",
                client.getWindow().getScaledWidth() / 2,
                client.getWindow().getScaledHeight() - 42,
                0xffffff);
        context.drawCenteredTextWithShadow(client.textRenderer,
                "Seeing from " + currentRoutine.onionLowerBound() + " to " + currentRoutine.onionUpperBound(),
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
        List<EditorButtonPart> parts = activeButton.getActions();
        int y = context.getScaledWindowHeight() / 2 - ((15 * parts.size()) - 5);
        for (EditorButtonPart part : parts) {
            context.drawTexture(part.getTexture(), 10, y, 0, 0, 0, 20, 20, 20, 20);
            context.drawTexture(OPTIONS_BORDER_TEXTURES.get(part.getAction()), 8, y - 2, 0, 0, 0, 28, 28, 28, 28);
            context.drawTextWithShadow(client.textRenderer, part.getDescription(), 38, y + 5, 0xffffff);
            y += 30;
        }
    }

    public void handleKeyboardInput(int key, Routine routine){
        EditorButton currentButton = buttons.get(currentIndex);
        switch (key){
            case 81 -> currentButton.perform(Actions.Q, routine);
            case 69 -> currentButton.perform(Actions.E, routine);
            case 90 -> currentButton.perform(Actions.Z, routine);
            case 67 -> currentButton.perform(Actions.C, routine);
        }
    }

    public void handleRightClick(Routine routine){

    }

    public void handleMouseScroll(double vertical){
        if (vertical > 0){
            if (currentIndex < buttons.size() - 1){
                currentIndex += 1;
            } else {
                currentIndex = 0;
            }
        } else if (vertical < 0) {
            if (currentIndex > 0){
                currentIndex -= 1;
            } else {
                currentIndex = buttons.size() - 1;
            }
        }
    }

    protected void addButton(EditorButton button){
        this.buttons.add(button);
    }

    public EditorMenu getPreviousMenu(){
        return previousMenu;
    }

    protected static Identifier of(String path){
        return new Identifier(ParticleStudio.MOD_ID, path);
    }

    public void onExit(Routine routine){

    }

}