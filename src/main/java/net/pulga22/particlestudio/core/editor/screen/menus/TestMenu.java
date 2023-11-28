package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.editor.Actions;
import net.pulga22.particlestudio.core.editor.EditorButton;
import net.pulga22.particlestudio.core.editor.EditorInputHandler;
import net.pulga22.particlestudio.core.editor.EditorMenu;

public class TestMenu extends EditorMenu {

    private static final Identifier qTexture = of("testq.png");
    private static final Identifier eTexture = of("teste.png");

    public TestMenu(EditorInputHandler editorInputHandler){
        super(null, editorInputHandler, "Menu1");
        addButton(new EditorButton(qTexture, "test1")
                .setAction(Actions.Q, qTexture, routine -> System.out.println("Q1"), "q1")
                .setAction(Actions.E, eTexture, routine -> System.out.println("E1"), "e1"));
        addButton(new EditorButton(qTexture, "test2")
                .setAction(Actions.Q, qTexture, routine -> System.out.println("Q2"), "q2")
                .setAction(Actions.E, eTexture, routine -> System.out.println("E2"), "e2"));
        addButton(new EditorButton(qTexture, "test3")
                .setAction(Actions.Q, qTexture, routine -> changeToMenu(new TestMenu2(this, editorInputHandler)), "prev"));
    }

}
