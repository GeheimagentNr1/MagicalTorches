package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.network.Network;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( MagicalTorches.MODID )
public class MagicalTorches {
	
	
	public static final String MODID = "magical_torches";
	
	public MagicalTorches() {
		
		Network.init();
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, ServerConfig.CONFIG );
	}
}
