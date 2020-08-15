package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.*;


public class SoundMufflingClientCapability {
	
	
	private static final TreeMap<DimensionType, TreeSet<SoundMuffler>> soundMufflers = new TreeMap<>(
		Comparator.comparingInt( DimensionType::getId ) );
	
	private static final ArrayList<SoundMufflerStorage> SOUND_MUFFLER_STORAGES = new ArrayList<>();
	
	public static void init() {
		
		Comparator<SoundMuffler> comparator = Comparator.comparing( SoundMuffler::getPos );
		DimensionType.getAll().forEach(
			dimensionType -> soundMufflers.put( dimensionType, new TreeSet<>( comparator ) ) );
	}
	
	public static void clear() {
		
		soundMufflers.clear();
		SOUND_MUFFLER_STORAGES.clear();
	}
	
	public static boolean shouldMuffleSound( ISound sound ) {
		
		if( !SOUND_MUFFLER_STORAGES.isEmpty() ) {
			for( SoundMufflerStorage soundMufflerStorage : SOUND_MUFFLER_STORAGES ) {
				soundMufflers.get( Objects.requireNonNull( soundMufflerStorage.getTileEntity().getWorld() )
					.getDimension().getType() ).add( soundMufflerStorage.getSoundMuffler() );
			}
			SOUND_MUFFLER_STORAGES.clear();
		}
		BlockPos sound_pos = new BlockPos( sound.getX(), sound.getY(), sound.getZ() );
		for( SoundMuffler soundMuffler : soundMufflers.get( getDimension() ) ) {
			if( soundMuffler.shouldMuffleSound( sound ) && RadiusHelper.isEventInRadiusOfBlock( sound_pos,
				soundMuffler.getPos(), soundMuffler.getRange() ) ) {
				return true;
			}
		}
		return false;
	}
	
	private static World getWorld() {
		
		return Minecraft.getInstance().world;
	}
	
	private static DimensionType getDimension() {
		
		return getWorld().getDimension().getType();
	}
	
	//package-private
	static void addSoundMuffler( TileEntity tileEntity, SoundMuffler soundMuffler ) {
		
		if( getWorld() == null ) {
			SOUND_MUFFLER_STORAGES.add( new SoundMufflerStorage( tileEntity, soundMuffler ) );
		} else {
			addSoundMuffler( soundMuffler );
		}
	}
	
	//package-private
	static void removeSoundMuffler( SoundMuffler soundMuffler ) {
		
		soundMufflers.get( getDimension() ).remove( soundMuffler );
	}
	
	private static void addSoundMuffler( SoundMuffler soundMuffler ) {
		
		soundMufflers.get( getDimension() ).add( soundMuffler );
	}
}
