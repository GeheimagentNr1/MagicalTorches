package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.resources.ResourceLocation;


public class ResourceLocationBuilder {
	
	
	public static ResourceLocation build( String registry_name ) {
		
		return new ResourceLocation( MagicalTorches.MODID, registry_name );
	}
}
