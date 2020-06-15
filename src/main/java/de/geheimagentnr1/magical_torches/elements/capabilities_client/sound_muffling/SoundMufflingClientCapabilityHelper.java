package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;


public class SoundMufflingClientCapabilityHelper {
	
	
	public static void addSoundMuffler( TileEntity tileEntity, SoundMuffler soundMuffler ) {
		
		DistExecutor.unsafeRunWhenOn( Dist.CLIENT, () -> () ->
			SoundMufflingClientCapability.addSoundMuffler( tileEntity, soundMuffler ) );
	}
	
	public static void removeSoundMuffler( SoundMuffler soundMuffler ) {
		
		DistExecutor.unsafeRunWhenOn( Dist.CLIENT, () -> () ->
			SoundMufflingClientCapability.removeSoundMuffler( soundMuffler ) );
	}
}
