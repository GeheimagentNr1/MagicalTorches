package de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.SoundMufflingClientCapabilityHelper;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.sound_mufflers.SoundMufflingTorchSoundMuffler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
	public void setWorldAndPos( @Nonnull World _world, @Nonnull BlockPos _pos ) {
		
		super.setWorldAndPos( _world, _pos );
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
