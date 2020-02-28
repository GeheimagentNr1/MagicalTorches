package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import net.minecraft.tileentity.TileEntity;


//package-private
class SoundMufflerStorage {
	
	
	private final TileEntity tileEntity;
	
	private final SoundMuffler soundMuffler;
	
	//package-private
	SoundMufflerStorage( TileEntity _tileEntity, SoundMuffler _soundMuffler ) {
		
		tileEntity = _tileEntity;
		soundMuffler = _soundMuffler;
	}
	
	//package-private
	TileEntity getTileEntity() {
		
		return tileEntity;
	}
	
	//package-private
	SoundMuffler getSoundMuffler() {
		
		return soundMuffler;
	}
}
