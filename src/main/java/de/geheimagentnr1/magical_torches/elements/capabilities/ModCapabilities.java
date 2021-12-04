package de.geheimagentnr1.magical_torches.elements.capabilities;

import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;


@SuppressWarnings( "StaticNonFinalField" )
public class ModCapabilities {
	
	
	public static Capability<ChickenEggSpawningCapability> CHICKEN_EGG_SPAWNING =
		CapabilityManager.get( new CapabilityToken<>() {
		
		} );
	
	public static Capability<SpawnBlockingCapability> SPAWN_BLOCKING =
		CapabilityManager.get( new CapabilityToken<>() {
		
		} );
	
	public static Capability<SoundMufflingCapability> SOUND_MUFFLING =
		CapabilityManager.get( new CapabilityToken<>() {
		
		} );
}
