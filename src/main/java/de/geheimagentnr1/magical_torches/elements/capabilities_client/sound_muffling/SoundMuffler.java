package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.math.BlockPos;


public abstract class SoundMuffler {
	
	
	private final BlockPos pos;
	
	protected SoundMuffler( BlockPos _pos ) {
		
		pos = _pos;
	}
	
	public BlockPos getPos() {
		
		return pos;
	}
	
	public abstract int getRange();
	
	public abstract boolean shouldMuffleSound( ISound sound );
}
