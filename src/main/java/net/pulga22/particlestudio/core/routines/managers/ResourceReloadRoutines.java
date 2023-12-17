package net.pulga22.particlestudio.core.routines.managers;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.ParticleStudio;
import net.pulga22.particlestudio.core.routines.Routine;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ResourceReloadRoutines implements SimpleSynchronousResourceReloadListener {

    private static final Identifier FABRIC_ID = new Identifier(ParticleStudio.MOD_ID, "routines");

    @Override
    public Identifier getFabricId() {
        return FABRIC_ID;
    }

    @Override
    public void reload(ResourceManager manager) {
        manager.findResources("routines", path -> path.getPath().endsWith(".rtn")).forEach((id, file) -> {
            try(InputStream inputStream = file.getInputStream();
                ObjectInputStream reader = new ObjectInputStream(inputStream)){
                Routine routine = (Routine) reader.readObject();


            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
