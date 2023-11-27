package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.editor.EditorButton;
import net.pulga22.particlestudio.core.editor.EditorInputHandler;
import net.pulga22.particlestudio.core.editor.EditorMenu;

public class TestMenu2 extends EditorMenu {

    private static final Identifier qTexture = of("testq.png");
    private static final Identifier cTexture = of("teste.png");

    public TestMenu2(EditorMenu previousMenu, EditorInputHandler editorInputHandler){
        super(previousMenu, editorInputHandler);
        addButton(new EditorButton(qTexture, routine -> System.out.println("Q1"))
                .setOnEPress(cTexture, routine -> System.out.println("E1")));
        addButton(new EditorButton(qTexture, routine -> System.out.println("Q2"))
                .setOnEPress(cTexture, routine -> System.out.println("E2")));
    }

}
