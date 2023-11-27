package net.pulga22.particlestudio.core.editor;

import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.routines.Routine;

import java.util.function.Consumer;

public abstract class EditorButtonDepre {

    protected Identifier currentTexture;
    public abstract void onQPressed(Routine routine);
    public abstract void onEPressed(Routine routine);
    public abstract void onZPressed(Routine routine);
    public abstract void onCPressed(Routine routine);
    public abstract void onRightClick(Routine routine);
    public abstract Identifier getDefaultTexture();
    public abstract Identifier getQTexture();
    public abstract Identifier getETexture();
    public abstract Identifier getZTexture();
    public abstract Identifier getCTexture();


    public static Builder builder(Identifier texture){
        return new Builder(texture);
    }

    public static class Builder{

        private final Identifier defaultTempTexture;
        private Consumer<Routine> onQPressed = routine -> {};
        private Identifier QTempTexture;
        private Consumer<Routine> onEPressed = routine -> {};
        private Identifier ETempTexture;
        private Consumer<Routine> onZPressed = routine -> {};
        private Identifier ZTempTexture;
        private Consumer<Routine> onCPressed = routine -> {};
        private Identifier CTempTexture;
        private Consumer<Routine> onRightClick = routine -> {};

        public Builder(Identifier defaultTexture){
            this.defaultTempTexture = defaultTexture;
        }

        public Builder onQPressed(Consumer<Routine> action){
            onQPressed = action;
            return this;
        }

        public Builder setQTexture(Identifier texture){
            QTempTexture = texture;
            return this;
        }

        public Builder onEPressed(Consumer<Routine> action){
            onEPressed = action;
            return this;
        }

        public Builder setETexture(Identifier texture){
            ETempTexture = texture;
            return this;
        }

        public Builder onZPressed(Consumer<Routine> action){
            onZPressed = action;
            return this;
        }

        public Builder setZTexture(Identifier texture){
            ZTempTexture = texture;
            return this;
        }

        public Builder onCPressed(Consumer<Routine> action){
            onCPressed = action;
            return this;
        }

        public Builder setCTexture(Identifier texture){
            CTempTexture = texture;
            return this;
        }

        public Builder onRightClick(Consumer<Routine> action){
            onRightClick = action;
            return this;
        }

        public EditorButtonDepre build(){
            return new EditorButtonDepre() {

                @Override
                public void onQPressed(Routine routine) {
                    onQPressed.accept(routine);
                    if (QTempTexture != null) currentTexture = QTempTexture;
                }

                @Override
                public void onEPressed(Routine routine) {
                    onEPressed.accept(routine);
                    if (ETempTexture != null) currentTexture = ETempTexture;
                }

                @Override
                public void onZPressed(Routine routine) {
                    onZPressed.accept(routine);
                    if (ZTempTexture != null) currentTexture = ZTempTexture;
                }

                @Override
                public void onCPressed(Routine routine) {
                    onCPressed.accept(routine);
                    if (CTempTexture != null) currentTexture = CTempTexture;
                }

                @Override
                public void onRightClick(Routine routine) {
                    onRightClick.accept(routine);
                    currentTexture = defaultTempTexture;
                }

                @Override
                public Identifier getDefaultTexture() {
                    return defaultTempTexture;
                }

                @Override
                public Identifier getQTexture() {
                    return QTempTexture;
                }

                @Override
                public Identifier getETexture() {
                    return ETempTexture;
                }

                @Override
                public Identifier getZTexture() {
                    return ZTempTexture;
                }

                @Override
                public Identifier getCTexture() {
                    return CTempTexture;
                }


            };
        }

    }

}
