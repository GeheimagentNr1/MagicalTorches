package de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.SoundMufflingClientCapabilityHelper;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.sound_mufflers.SoundMufflingTorchSoundMuffler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nonnull;


public class SoundMufflingTorchTile extends TileEntity {
	
	
	public SoundMufflingTorchTile() {
		
		super( ModBlocks.SOUND_MUFFLING_TORCH_TILE );
	}
	
	@SuppressWarnings( "unused" )
	public SoundMufflingTorchTile( TileEntityType<? extends SoundMufflingTorchTile> _type ) {
		
		super( _type );
	}
	
	@Override
	public void setPos( @Nonnull BlockPos posIn ) {
		
		super.setPos( posIn );
		SoundMufflingClientCapabilityHelper.addSoundMuffler( this,
			SoundMufflingTorchSoundMuffler.FACTORY.buildSoundMuffler( pos ) );
	}
	
	@Override
	public void remove() {
		
		SoundMufflingClientCapabilityHelper.removeSoundMuffler(
			SoundMufflingTorchSoundMuffler.FACTORY.buildSoundMuffler( pos ) );
		super.remove();
	}
}
