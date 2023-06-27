package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;


public abstract class SoundMuffler extends CapabilityData {
	
	
	protected SoundMuffler( @NotNull BlockPos _pos ) {
		
		super( _pos );
	}
	
	public abstract int getRange();
	
	public abstract boolean shouldMuffleSound( @NotNull SoundInstance sound );
	
	@NotNull
	public String getSoundCategoriesString( @NotNull ServerConfig serverConfig ) {
		
		if( serverConfig.getSoundMufflingTorchToMuffleSounds().isEmpty() ) {
			return "";
		}
		return serverConfig.getSoundMufflingTorchToMuffleSounds()
			.stream()
			.map( SoundSource::getName )
			.collect( Collectors.joining( ", " ) );
	}
}
