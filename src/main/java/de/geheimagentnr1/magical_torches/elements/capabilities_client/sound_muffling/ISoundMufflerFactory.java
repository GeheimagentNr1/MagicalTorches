package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import net.minecraft.util.math.BlockPos;


@FunctionalInterface
public interface ISoundMufflerFactory {
	
	
	SoundMuffler buildSoundMuffler( BlockPos pos );
}
