package net.pulga22.particlestudio;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.pulga22.particlestudio.command.AllCommands;
import net.pulga22.particlestudio.items.ParticleController;
import net.pulga22.particlestudio.networking.AllPackets;
import net.pulga22.particlestudio.core.routines.ParticleRoutinesManager;
import net.pulga22.particlestudio.core.routines.WorldRoutines;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParticleStudio implements ModInitializer {

	public static final String MOD_ID = "particlestudio";
    public static final Logger LOGGER = LoggerFactory.getLogger("particlestudio");

	@Override
	public void onInitialize() {
		AllCommands.register();
		AllPackets.registerC2SPackets();

		Registry.register(
				Registries.ITEM,
				new Identifier(ParticleStudio.MOD_ID, "particle_controller"),
				new ParticleController(new FabricItemSettings())
		);

		ServerWorldEvents.LOAD.register((server, world) -> {
			ParticleRoutinesManager.getInstance().loadWorld(world, new WorldRoutines());
		});

		LOGGER.info("Hello Fabric world!");
	}
}