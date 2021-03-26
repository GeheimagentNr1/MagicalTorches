package de.geheimagentnr1.magical_torches.elements.capabilities;

import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;


@SuppressWarnings( "StaticNonFinalField" )
public class ModCapabilities {
	
	
	@CapabilityInject( ChickenEggSpawningCapability.class )
	public static Capability<ChickenEggSpawningCapability> CHICKEN_EGG_SPAWNING;
	
	@CapabilityInject( SpawnBlockingCapability.class )
	public static Capability<SpawnBlockingCapability> SPAWN_BLOCKING;
	
	@CapabilityInject( SoundMufflingCapability.class )
	public static Capability<SoundMufflingCapability> SOUND_MUFFLING;
}
