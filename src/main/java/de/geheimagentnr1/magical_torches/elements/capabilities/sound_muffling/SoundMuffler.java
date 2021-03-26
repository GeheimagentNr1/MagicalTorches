package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.math.BlockPos;


public abstract class SoundMuffler extends CapabilityData {
	
	
	protected SoundMuffler( BlockPos _pos ) {
		
		super( _pos );
	}
	
	public abstract int getRange();
	
	public abstract boolean shouldMuffleSound( ISound sound );
	
	public abstract String getSoundCategoriesString();
}
