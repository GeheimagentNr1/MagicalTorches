package de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling;

import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

import java.util.*;


public class SoundMufflingClientCapability {
	
	
	private final static TreeMap<DimensionType, TreeSet<SoundMuffler>> soundMufflers = new TreeMap<>(
		Comparator.comparingInt( DimensionType::getId ) );
	
	private final static ArrayList<SoundMufflerStorage> SOUND_MUFFLER_STORAGES = new ArrayList<>();
	
	public static void init() {
		
		Comparator<SoundMuffler> comparator = Comparator.comparing( SoundMuffler::getPos );
		for( World world : ServerLifecycleHooks.getCurrentServer().getWorlds() ) {
			soundMufflers.put( world.getDimension().getType(), new TreeSet<>( comparator ) );
		}
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
	
	public static void addSoundMuffler( TileEntity tileEntity, SoundMuffler soundMuffler ) {
		
		DistExecutor.runWhenOn( Dist.CLIENT, () -> () -> {
			if( getWorld() == null ) {
				SOUND_MUFFLER_STORAGES.add( new SoundMufflerStorage( tileEntity, soundMuffler ) );
			} else {
				addSoundMuffler( soundMuffler );
			}
		} );
	}
	
	public static void removeSoundMuffler( SoundMuffler soundMuffler ) {
		
		DistExecutor.runWhenOn( Dist.CLIENT, () -> () -> soundMufflers.get( getDimension() ).remove( soundMuffler ) );
	}
	
	public static void addSoundMuffler( SoundMuffler soundMuffler ) {
		
		DistExecutor.runWhenOn( Dist.CLIENT, () -> () -> soundMufflers.get( getDimension() ).add( soundMuffler ) );
	}
}
