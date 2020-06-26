package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;


public class SoundMufflingClientCapability {
	
	
	private final static TreeMap<RegistryKey<World>, TreeSet<SoundMuffler>> soundMufflers = new TreeMap<>(
		Comparator.comparing( RegistryKey::func_240901_a_ ) );
	
	private final static ArrayList<SoundMufflerStorage> SOUND_MUFFLER_STORAGES = new ArrayList<>();
	
	public static void init() {
		
		Comparator<SoundMuffler> comparator = Comparator.comparing( SoundMuffler::getPos );
		Objects.requireNonNull( Minecraft.getInstance().getConnection() ).func_239164_m_().forEach(
			dimension -> soundMufflers.put( dimension, new TreeSet<>( comparator ) ) );
	}
	
	public static void clear() {
		
		soundMufflers.clear();
		SOUND_MUFFLER_STORAGES.clear();
	}
	
	public static boolean shouldMuffleSound( ISound sound ) {
		
		if( !SOUND_MUFFLER_STORAGES.isEmpty() ) {
			for( SoundMufflerStorage soundMufflerStorage : SOUND_MUFFLER_STORAGES ) {
				soundMufflers.get( Objects.requireNonNull( soundMufflerStorage.getTileEntity().getWorld() )
					.func_234923_W_() ).add( soundMufflerStorage.getSoundMuffler() );
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
	
	private static RegistryKey<World> getDimension() {
		
		return getWorld().func_234923_W_();
	}
	
	public static void addSoundMuffler( TileEntity tileEntity, SoundMuffler soundMuffler ) {
		
		if( getWorld() == null ) {
			SOUND_MUFFLER_STORAGES.add( new SoundMufflerStorage( tileEntity, soundMuffler ) );
		} else {
			addSoundMuffler( soundMuffler );
		}
	}
	
	public static void removeSoundMuffler( SoundMuffler soundMuffler ) {
		
		soundMufflers.get( getDimension() ).remove( soundMuffler );
	}
	
	private static void addSoundMuffler( SoundMuffler soundMuffler ) {
		
		soundMufflers.get( getDimension() ).add( soundMuffler );
	}
}
