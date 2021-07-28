package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;

import java.util.stream.Collectors;


public abstract class SoundMuffler extends CapabilityData {
	
	
	protected SoundMuffler( BlockPos _pos ) {
		
		super( _pos );
	}
	
	public abstract int getRange();
	
	public abstract boolean shouldMuffleSound( SoundInstance sound );
	
	public String getSoundCategoriesString() {
		
		if( ServerConfig.getSoundMufflingTorchToMuffleSounds().isEmpty() ) {
			return "";
		}
		return ServerConfig.getSoundMufflingTorchToMuffleSounds()
			.stream()
			.map( SoundSource::getName )
			.collect( Collectors.joining(", ") );
	}
}
