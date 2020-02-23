package de.geheimagentnr1.magical_torches.helper;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.util.ResourceLocation;


public class ResourceLocationBuilder {
	
	
	public static ResourceLocation build( String registry_name ) {
		
		return new ResourceLocation( MagicalTorches.MODID, registry_name );
	}
}
