package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocksRegisterFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.elements.creative_mod_tabs.ModCreativeModeTabRegisterFactory;
import de.geheimagentnr1.magical_torches.handlers.LateConfigInitilizationHandler;
import de.geheimagentnr1.magical_torches.handlers.SoundMufflingHandler;
import de.geheimagentnr1.magical_torches.handlers.SpawnBlockingHandler;
import de.geheimagentnr1.magical_torches.network.Network;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;


@Mod( MagicalTorches.MODID )
public class MagicalTorches extends AbstractMod {
	
	
	@NotNull
	public static final String MODID = "magical_torches";
	
	@NotNull
	public static final String SERVER_CONFIG_NOT_FOUND_ERROR_MESSAGE = "MoreMobGriefingOptions ServerConfig not found";
	
	@NotNull
	@Override
	public String getModId() {
		
		return MODID;
	}
	
	@Override
	protected void initMod() {
		
		ModBlocksRegisterFactory modBlocksRegisterFactory = registerEventHandler( new ModBlocksRegisterFactory() );
		registerEventHandler( new ModCapabilitiesRegisterFactory( this ) );
		registerEventHandler( new ModCreativeModeTabRegisterFactory( modBlocksRegisterFactory ) );
		registerEventHandler( new LateConfigInitilizationHandler( this ) );
		registerEventHandler( new SoundMufflingHandler() );
		registerEventHandler( new SpawnBlockingHandler() );
		registerEventHandler( Network.getInstance() );
	}
	
	public void initMobgriefingConfig() {
		
		registerConfig( ServerConfig::new );
	}
}
