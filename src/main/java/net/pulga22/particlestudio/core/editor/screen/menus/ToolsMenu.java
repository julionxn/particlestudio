package net.pulga22.particlestudio.core.editor.screen.menus;

import net.minecraft.client.MinecraftClient;
import net.pulga22.particlestudio.core.editor.handlers.EditorHandler;
import net.pulga22.particlestudio.core.editor.components.Actions;
import net.pulga22.particlestudio.core.editor.components.EditorButton;
import net.pulga22.particlestudio.core.editor.components.EditorMenu;
import net.pulga22.particlestudio.core.editor.screen.gui.SelectedParticleMenu;

public class ToolsMenu extends EditorMenu {

    public ToolsMenu(EditorHandler editorHandler) {
        super(editorHandler, "Tools");
        addButton(EditorButton.builder("tools/points", "Points")
                .setAction(Actions.Q, routine -> routine.addParticlePoint(editorHandler), "Add", builder -> builder).build());
        addButton(EditorButton.builder("tools/particles", "Particles")
                .setAction(Actions.Q, routine -> {
                    MinecraftClient client = MinecraftClient.getInstance();
                    if (client == null) return;
                    editorHandler.setScrollActive(false);
                    client.setScreen(new SelectedParticleMenu(editorHandler));
                }, "Cambiar actual", builder -> builder).build());
        addButton(EditorButton.builder("tools/paste", "Paste")
                .setAction(Actions.Q, routine -> {
                    return;
                    /*
                    int current = routine.getTimeline().getCurrentEditingTick();
                    List<ParticlePoint> points = editorHandler.getClipboard();
                    if (points.isEmpty()) return;
                    points.forEach(point -> routine.addParticlePoint(point.tick + current, point));
                     */
                }, "Paste", builder ->  builder).build());
    }

}
