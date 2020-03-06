package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class SpawnBlockerNBTHelper {
	
	
	private final static String registryNameName = "registry_name";
	
	private final static String xName = "x";
	
	private final static String yName = "y";
	
	private final static String zName = "z";
	
	public static ListNBT serializeSpawnBlockers( TreeSet<SpawnBlocker> spawnBlockers ) {
		
		ListNBT nbt = new ListNBT();
		
		for( SpawnBlocker spawnBlocker : spawnBlockers ) {
			BlockPos pos = spawnBlocker.getPos();
			CompoundNBT compoundNBT = new CompoundNBT();
			compoundNBT.putString( registryNameName, spawnBlocker.getRegistryName().toString() );
			compoundNBT.putInt( xName, pos.getX() );
			compoundNBT.putInt( yName, pos.getY() );
			compoundNBT.putInt( zName, pos.getZ() );
			nbt.add( compoundNBT );
		}
		return nbt;
	}
	
	public static TreeSet<SpawnBlocker> deserializeSpawnBlockers( ListNBT nbt,
		TreeMap<ResourceLocation, ISpawnBlockFactory> SPAWN_BLOCKING_REGISTERY ) {
		
		TreeSet<SpawnBlocker> spawnBlockers = new TreeSet<>( Comparator.comparing( SpawnBlocker::getPos ) );
		for( INBT inbt : nbt ) {
			if( inbt instanceof CompoundNBT ) {
				CompoundNBT compoundNBT = (CompoundNBT)inbt;
				if( compoundNBT.contains( registryNameName, Constants.NBT.TAG_STRING ) ) {
					String registry_name_string = compoundNBT.getString( registryNameName );
					ResourceLocation registry_name = ResourceLocation.tryCreate( registry_name_string );
					if( registry_name != null &&
						compoundNBT.contains( xName, Constants.NBT.TAG_INT ) &&
						compoundNBT.contains( yName, Constants.NBT.TAG_INT ) &&
						compoundNBT.contains( zName, Constants.NBT.TAG_INT ) ) {
						BlockPos pos = new BlockPos( new BlockPos( compoundNBT.getInt( xName ),
							compoundNBT.getInt( yName ), compoundNBT.getInt( zName ) ) );
						ISpawnBlockFactory factory = SPAWN_BLOCKING_REGISTERY.get( registry_name );
						if( factory != null ) {
							spawnBlockers.add( factory.buildSpawnBlocker( pos ) );
						}
					}
				}
			}
		}
		return spawnBlockers;
	}
}
