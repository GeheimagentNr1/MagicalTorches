package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.sound_mufflers;

import de.geheimagentnr1.magical_torches.config.ClientConfigHolder;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling.SoundMufflingTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.ISoundMufflerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;


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
		
		return ClientConfigHolder.getSoundMufflingTorchRange();
	}
	
	@Override
	public boolean shouldMuffleSound( ISound sound ) {
		
		return ClientConfigHolder.doesSoundMufflingTorchToMuffleSoundsContainsSound( sound );
	}
	
	@Override
	public String getSoundCategoriesString() {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		if( !ClientConfigHolder.getSoundMufflingTorchToMuffleSounds().isEmpty() ) {
			stringBuilder.append( ClientConfigHolder.getSoundMufflingTorchToMuffleSounds().get( 0 ).getName() );
			for( SoundCategory soundCategory : ClientConfigHolder.getSoundMufflingTorchToMuffleSounds() ) {
				stringBuilder.append( ", " ).append( soundCategory.getName() );
			}
		}
		return stringBuilder.toString();
	}
}
