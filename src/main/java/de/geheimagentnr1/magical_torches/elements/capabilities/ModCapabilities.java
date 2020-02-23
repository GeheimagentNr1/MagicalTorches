package de.geheimagentnr1.magical_torches.elements.capabilities;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;


@SuppressWarnings( { "PublicField", "StaticNonFinalField", "unused" } )
public class ModCapabilities {
	
	
	@CapabilityInject( SpawnBlockingCapability.class )
	public static Capability<SpawnBlockingCapability> SPAWN_BLOCKING;
}
