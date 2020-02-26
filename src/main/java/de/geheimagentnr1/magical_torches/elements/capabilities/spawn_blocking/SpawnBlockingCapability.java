package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.helper.ResourceLocationBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class SpawnBlockingCapability implements ICapabilitySerializable<ListNBT> {
	
	
	public final static ResourceLocation registry_name = ResourceLocationBuilder.build( "spawn_blocking" );
	
	private final LazyOptional<SpawnBlockingCapability> capability = LazyOptional.of( () -> this );
	
	private TreeSet<SpawnBlocker> spawnBlockers = new TreeSet<>( Comparator.comparing( SpawnBlocker::getPos ) );
	
	private final static TreeMap<ResourceLocation, ISpawnBlockFactory> SPAWN_BLOCKING_REGISTERY = new TreeMap<>();
	
	private final static String registryNameName = "registry_name";
	
	private final static String xName = "x";
	
	private final static String yName = "y";
	
	private final static String zName = "z";
	
	public static void registerSpawnBlocker( ResourceLocation _registry_name, ISpawnBlockFactory factory ) {
		
		SPAWN_BLOCKING_REGISTERY.put( _registry_name, factory );
	}
	
	public boolean shouldBlockEntitySpawn( Entity entity ) {
		
		BlockPos spawn_pos = entity.getPosition();
		for( SpawnBlocker spawnBlocker : spawnBlockers ) {
			if( spawnBlocker.shouldBlockEntity( entity ) ) {
				BlockPos pos = spawnBlocker.getPos();
				int range = spawnBlocker.getRange();
				if( pos.getX() - range <= spawn_pos.getX() && pos.getX() + range >= spawn_pos.getX() &&
					pos.getY() - range <= spawn_pos.getY() && pos.getY() + range >= spawn_pos.getY() &&
					pos.getZ() - range <= spawn_pos.getZ() && pos.getZ() + range >= spawn_pos.getZ() ) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability( @Nonnull Capability<T> cap, @Nullable Direction side ) {
		
		if( cap == ModCapabilities.SPAWN_BLOCKING ) {
			return capability.cast();
		}
		return LazyOptional.empty();
	}
	
	@Override
	public ListNBT serializeNBT() {
		
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
	
	@Override
	public void deserializeNBT( ListNBT nbt ) {
		
		spawnBlockers = new TreeSet<>( Comparator.comparing( SpawnBlocker::getPos ) );
		for( INBT inbt : nbt ) {
			if( inbt instanceof CompoundNBT ) {
				CompoundNBT compoundNBT = (CompoundNBT)inbt;
				if( compoundNBT.contains( registryNameName, Constants.NBT.TAG_STRING ) ) {
					String registry_name_string = compoundNBT.getString( registryNameName );
					if( ResourceLocation.isResouceNameValid( registry_name_string ) &&
						compoundNBT.contains( xName, Constants.NBT.TAG_INT ) &&
						compoundNBT.contains( yName, Constants.NBT.TAG_INT ) &&
						compoundNBT.contains( zName, Constants.NBT.TAG_INT ) ) {
						ResourceLocation registry_name = new ResourceLocation( registry_name_string );
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
	}
	
	public void addSpawnBlocker( SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.add( spawnBlocker );
	}
	
	public void removeSpawnBlocker( SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.remove( spawnBlocker );
	}
}
