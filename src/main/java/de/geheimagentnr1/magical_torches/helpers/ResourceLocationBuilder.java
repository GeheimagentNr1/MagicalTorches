package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class ResourceLocationBuilder {
	
	
	@NotNull
	public static ResourceLocation build( @NotNull String registry_name ) {
		
		return new ResourceLocation( MagicalTorches.MODID, registry_name );
	}
}
