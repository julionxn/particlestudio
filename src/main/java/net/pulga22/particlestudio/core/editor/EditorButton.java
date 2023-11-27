package net.pulga22.particlestudio.core.editor;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.function.Consumer;

public class EditorButton {

    private Identifier currentTexture;
    private final Consumer<Routine> onQPress;
    private Consumer<Routine> onEPress = routine -> {};
    private Consumer<Routine> onZPress = routine -> {};
    private Consumer<Routine> onCPress = routine -> {};
    private final Identifier QTexture;
    private Identifier ETexture;
    private Identifier ZTexture;
    private Identifier CTexture;
    private boolean hasEAction = false;
    private boolean hasZAction = false;
    private boolean hasCAction = false;

    public EditorButton(Identifier QTexture, Consumer<Routine> QAction){
        this.QTexture = QTexture;
        this.onQPress = QAction;
        this.currentTexture = QTexture;
    }

    public EditorButton setOnEPress(Identifier texture, Consumer<Routine> action){
        this.ETexture = texture;
        this.onEPress = action;
        this.hasEAction = true;
        return this;
    }

    public EditorButton setOnZPress(Identifier texture, Consumer<Routine> action){
        this.ZTexture = texture;
        this.onZPress = action;
        this.hasZAction = true;
        return this;
    }

    public EditorButton setOnCPress(Identifier texture, Consumer<Routine> action){
        this.CTexture = texture;
        this.onCPress = action;
        this.hasCAction = true;
        return this;
    }

    public void onQPress(Routine routine){
        this.onQPress.accept(routine);
        this.currentTexture = QTexture;
    }

    public void onEPress(Routine routine){
        if (!hasEAction) return;
        this.onEPress.accept(routine);
        this.currentTexture = ETexture;
    }

    public void onZPress(Routine routine){
        if (!hasZAction) return;
        this.onZPress.accept(routine);
        this.currentTexture = ZTexture;
    }

    public void onCPress(Routine routine){
        if (!hasCAction) return;
        this.onCPress.accept(routine);
        this.currentTexture = CTexture;
    }

    public Identifier getCurrentTexture(){
        return currentTexture;
    }

    public Identifier getQTexture(){
        return QTexture;
    }

    public Identifier getETexture(){
        return ETexture;
    }

    public Identifier getZTexture(){
        return ZTexture;
    }

    public Identifier getCTexture(){
        return CTexture;
    }

}
