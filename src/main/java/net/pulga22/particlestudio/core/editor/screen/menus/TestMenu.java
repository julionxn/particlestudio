package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.editor.EditorButton;
import net.pulga22.particlestudio.core.editor.EditorInputHandler;
import net.pulga22.particlestudio.core.editor.EditorMenu;

public class TestMenu extends EditorMenu {

    private static final Identifier qTexture = of("testq.png");
    private static final Identifier cTexture = of("teste.png");

    public TestMenu(EditorInputHandler editorInputHandler){
        super(null, editorInputHandler);
        addButton(new EditorButton(qTexture, routine -> System.out.println("Q1"))
                .setOnEPress(cTexture, routine -> System.out.println("E1")));
        addButton(new EditorButton(qTexture, routine -> System.out.println("Q2"))
                .setOnEPress(cTexture, routine -> System.out.println("E2")));
        addButton(new EditorButton(qTexture, routine -> changeToMenu(new TestMenu2(this, editorInputHandler))));
    }

}
