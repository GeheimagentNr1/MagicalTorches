package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;


public class ResourceLocationBuilder {
	
	
	@NotNull
	public static Identifier build( @NotNull String registry_name ) {
		
		return Identifier.fromNamespaceAndPath( MagicalTorches.MODID, registry_name );
	}
}
