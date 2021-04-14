package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.network.Network;
import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import org.apache.commons.lang3.tuple.Pair;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( MagicalTorches.MODID )
public class MagicalTorches {
	
	
	public static final String MODID = "magical_torches";
	
	public MagicalTorches() {
		
		Network.init();
		ModLoadingContext.get().registerConfig( ModConfig.Type.SERVER, ServerConfig.CONFIG );
		ModLoadingContext.get().registerExtensionPoint(
			ExtensionPoint.DISPLAYTEST,
			() -> Pair.of( () -> FMLNetworkConstants.IGNORESERVERONLY, ( remote, isServer ) -> true )
		);
	}
}
