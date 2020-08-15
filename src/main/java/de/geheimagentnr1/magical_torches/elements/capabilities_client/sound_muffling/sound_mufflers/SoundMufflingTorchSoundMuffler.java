package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.sound_mufflers;

import de.geheimagentnr1.magical_torches.config.ModConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.ISoundMufflerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.SoundMuffler;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;


public class SoundMufflingTorchSoundMuffler extends SoundMuffler {
	
	
	public static final ISoundMufflerFactory FACTORY = SoundMufflingTorchSoundMuffler::new;
	
	private static final ArrayList<SoundCategory> toMuffleSounds = new ArrayList<>(
		Arrays.asList( SoundCategory.HOSTILE, SoundCategory.NEUTRAL, SoundCategory.BLOCKS ) );
	
	private SoundMufflingTorchSoundMuffler( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public int getRange() {
		
		return ModConfig.getSoundMufflingTorchRange();
	}
	
	@Override
	public boolean shouldMuffleSound( ISound sound ) {
		
		return toMuffleSounds.contains( sound.getCategory() );
	}
}
