package net.pulga22.particlestudio;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.core.routines.managers.ParticleRoutinesManager;
import net.pulga22.particlestudio.items.ParticleController;
import net.pulga22.particlestudio.networking.AllPackets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParticleStudio implements ModInitializer {

	public static final String MOD_ID = "particlestudio";
    public static final Logger LOGGER = LoggerFactory.getLogger("particlestudio");

	@Override
	public void onInitialize() {
		AllPackets.registerC2SPackets();

		Registry.register(
				Registries.ITEM,
				new Identifier(ParticleStudio.MOD_ID, "particle_controller"),
				new ParticleController(new FabricItemSettings())
		);

		ServerWorldEvents.LOAD.register((server, world) -> ParticleRoutinesManager.getInstance().loadWorld(world));
		ServerWorldEvents.UNLOAD.register((server, world) -> ParticleRoutinesManager.getInstance().saveWorld(world));
		ServerLifecycleEvents.END_DATA_PACK_RELOAD.register((server, resourceManager, success) ->
				server.getWorlds().forEach(world ->
						ParticleRoutinesManager.getInstance().saveWorld(world)
				)
		);

		ParticleRoutinesManager.getInstance().prepareParticles();

		LOGGER.info("Hello Fabric world!");
	}
}