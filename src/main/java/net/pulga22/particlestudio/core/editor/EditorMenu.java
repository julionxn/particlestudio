package net.pulga22.particlestudio.core.editor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.ArrayList;
import java.util.List;

public class EditorMenu {

    private static final Identifier ACTIVE_BUTTON_TEXTURE = of("active.png");
    private final List<EditorButton> buttons = new ArrayList<>();
    private int currentIndex = 0;
    private final EditorMenu previousMenu;
    private final EditorInputHandler editorInputHandler;

    public EditorMenu(EditorMenu previousMenu, EditorInputHandler editorInputHandler){
        this.previousMenu = previousMenu;
        this.editorInputHandler = editorInputHandler;
    }

    public void render(DrawContext context){
        int buttonsAmount = buttons.size();
        int buttonGap = 10;
        int buttonScale = 20;
        int x = context.getScaledWindowWidth() / 2 - ((15 * buttonsAmount) - 5);

        for (int i = 0; i < buttons.size(); i++) {
            if (i == currentIndex){
                context.drawTexture(ACTIVE_BUTTON_TEXTURE, x - 2, 40 - 2, 0, 0, 0, 24, 24, 24, 24);
            }
            EditorButton currentButton = buttons.get(i);
            context.drawTexture(currentButton.getCurrentTexture(), x, 40, 0, 0, 0, buttonScale, buttonScale, buttonScale, buttonScale);
            x += buttonGap + buttonScale;
        }

    }

    public void handleKeyboardInput(int key, Routine routine){
        EditorButton currentButton = buttons.get(currentIndex);
        switch (key){
            case 81 -> currentButton.onQPress(routine);
            case 69 -> currentButton.onEPress(routine);
            case 90 -> currentButton.onZPress(routine);
            case 67 -> currentButton.onCPress(routine);
        }
    }

    public void handleRightClick(Routine routine){

    }

    // 1.0 -> up || -1.0 -> down ||
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

    protected void changeToMenu(EditorMenu editorMenu){
        editorInputHandler.changeCurrentMenu(editorMenu);
    }

    protected static Identifier of(String path){
        return new Identifier(ParticleStudio.MOD_ID, path);
    }

}
