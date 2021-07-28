package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.sound_mufflers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling.SoundMufflingTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.ISoundMufflerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;


public class SoundMufflingTorchSoundMuffler extends SoundMuffler {
	
	
	public static final ResourceLocation registry_name =
		ResourceLocationBuilder.build( SoundMufflingTorch.registry_name );
	
	public static final ISoundMufflerFactory FACTORY = SoundMufflingTorchSoundMuffler::new;
	
	public SoundMufflingTorchSoundMuffler( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ServerConfig.getSoundMufflingTorchRange();
	}
	
	@Override
	public boolean shouldMuffleSound( SoundInstance sound ) {
		
		return ServerConfig.getSoundMufflingTorchToMuffleSounds().contains( sound.getSource() );
	}
}
