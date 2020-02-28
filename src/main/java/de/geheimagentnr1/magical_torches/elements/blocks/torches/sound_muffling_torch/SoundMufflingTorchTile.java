package de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling_torch;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.SoundMufflingClientCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.sound_mufflers.SoundMufflingTorchSoundMuffler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;


public class SoundMufflingTorchTile extends TileEntity {
	
	
	public SoundMufflingTorchTile() {
		
		super( ModBlocks.SOUND_MUFFLING_TORCH_TILE );
	}
	
	@SuppressWarnings( "unused" )
	public SoundMufflingTorchTile( TileEntityType<? extends SoundMufflingTorchTile> _type ) {
		
		super( _type );
	}
	
	@Override
	public void read( CompoundNBT compound ) {
		
		super.read( compound );
		SoundMufflingClientCapability.addSoundMuffler( this,
			SoundMufflingTorchSoundMuffler.FACTORY.buildSoundMuffler( pos ) );
	}
	
	@Override
	public void remove() {
		
		SoundMufflingClientCapability.removeSoundMuffler(
			SoundMufflingTorchSoundMuffler.FACTORY.buildSoundMuffler( pos ) );
		super.remove();
	}
}
