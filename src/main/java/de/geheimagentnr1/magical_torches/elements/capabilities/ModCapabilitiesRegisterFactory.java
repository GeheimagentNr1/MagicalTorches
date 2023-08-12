package de.geheimagentnr1.magical_torches.elements.capabilities;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.elements.capabilities.CapabilitiesRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.registry.RegistryEntry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class ModCapabilitiesRegisterFactory extends CapabilitiesRegisterFactory {
	
	
	@NotNull
	public static final Capability<ChickenEggSpawningCapability> CHICKEN_EGG_SPAWNING =
		CapabilityManager.get( new CapabilityToken<>() {
		
		} );
	
	@NotNull
	public static final Capability<SpawnBlockingCapability> SPAWN_BLOCKING =
		CapabilityManager.get( new CapabilityToken<>() {
		
		} );
	
	@NotNull
	public static final Capability<SoundMufflingCapability> SOUND_MUFFLING =
		CapabilityManager.get( new CapabilityToken<>() {
		
		} );
	
	@NotNull
	private final AbstractMod abstractMod;
	
	private ServerConfig serverConfig;
	
	@NotNull
	private ServerConfig serverConfig() {
		
		if( serverConfig == null ) {
			serverConfig = abstractMod.getConfig( ModConfig.Type.SERVER, ServerConfig.class )
				.orElseThrow( () -> new IllegalStateException( MagicalTorches.SERVER_CONFIG_NOT_FOUND_ERROR_MESSAGE ) );
		}
		return serverConfig;
	}
	
	public ModCapabilitiesRegisterFactory( @NotNull AbstractMod abstractMod ) {
		
		super( abstractMod );
		this.abstractMod = abstractMod;
	}
	
	@NotNull
	@Override
	protected List<Class<?>> capabilityClasses() {
		
		return List.of(
			ChickenEggSpawningCapability.class,
			SpawnBlockingCapability.class,
			SoundMufflingCapability.class
		);
	}
	
	@NotNull
	@Override
	protected List<RegistryEntry<ICapabilityProvider>> capabilities() {
		
		return List.of(
			RegistryEntry.create(
				ChickenEggSpawningCapability.registry_name,
				new ChickenEggSpawningCapability( serverConfig() )
			),
			RegistryEntry.create( SpawnBlockingCapability.registry_name, new SpawnBlockingCapability() ),
			RegistryEntry.create( SoundMufflingCapability.registry_name, new SoundMufflingCapability() )
		);
	}
}
