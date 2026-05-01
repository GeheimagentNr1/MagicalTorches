package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import de.geheimagentnr1.magical_torches.elements.creative_mod_tabs.ModCreativeModeTabs;
import de.geheimagentnr1.magical_torches.handlers.ClientSoundMufflingHandler;
import de.geheimagentnr1.magical_torches.handlers.CommonSoundMufflingHandler;
import de.geheimagentnr1.magical_torches.handlers.SpawnBlockingHandler;
import de.geheimagentnr1.magical_torches.network.Network;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.NotNull;


@Mod( MagicalTorches.MODID )
public class MagicalTorches {
	
	
	@NotNull
	public static final String MODID = "magical_torches";
	
	@NotNull
	public static final String SERVER_CONFIG_NOT_FOUND_ERROR_MESSAGE = "MagicalTorches ServerConfig not found";
	
	public MagicalTorches( IEventBus modEventBus, ModContainer modContainer ) {
		
		// Register blocks and items
		ModBlocks.register( modEventBus );
		ModCreativeModeTabs.register( modEventBus );
		ModAttachments.register( modEventBus );
		
		// Register mod event listeners
		modEventBus.addListener( Network::onRegisterPayloadHandlers );
		
		// Register forge event listeners
		NeoForge.EVENT_BUS.register( new CommonSoundMufflingHandler() );
		if( FMLLoader.getDist() == Dist.CLIENT ) {
			NeoForge.EVENT_BUS.register( new ClientSoundMufflingHandler() );
		}
		NeoForge.EVENT_BUS.register( new SpawnBlockingHandler() );
		
		// Register config
		modContainer.registerConfig( ModConfig.Type.SERVER, ServerConfig.SPEC );
	}
}
