package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.network.Network;
import net.minecraftforge.fml.common.Mod;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( MagicalTorches.MODID )
public class MagicalTorches {
	
	
	public static final String MODID = "magical_torches";
	
	public MagicalTorches() {
		
		Network.init();
	}
}
