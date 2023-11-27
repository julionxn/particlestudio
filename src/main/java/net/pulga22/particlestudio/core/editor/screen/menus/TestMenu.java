package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.client.gui.DrawContext;
import net.pulga22.particlestudio.core.editor.EditorButton;
import net.pulga22.particlestudio.core.editor.EditorMenu;

public class TestMenu extends EditorMenu {

    public TestMenu(){
        addButton(new EditorButton(null, routine -> System.out.println("Q"))
                .setOnCPress(null, routine -> System.out.println("C"))
                .setOnEPress(null, routine -> System.out.println("E"))
                .setOnZPress(null, routine -> System.out.println("Z")));
    }

    @Override
    public void render(DrawContext context) {

    }

}
