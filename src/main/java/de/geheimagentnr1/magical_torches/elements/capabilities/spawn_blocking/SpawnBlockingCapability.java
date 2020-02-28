package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
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
	
	public static void registerSpawnBlocker( ResourceLocation _registry_name, ISpawnBlockFactory factory ) {
		
		SPAWN_BLOCKING_REGISTERY.put( _registry_name, factory );
	}
	
	public boolean shouldBlockEntitySpawn( Entity entity ) {
		
		BlockPos spawn_pos = entity.getPosition();
		for( SpawnBlocker spawnBlocker : spawnBlockers ) {
			if( spawnBlocker.shouldBlockEntity( entity )  && RadiusHelper.isEventInRadiusOfBlock( spawn_pos,
				spawnBlocker.getPos(), spawnBlocker.getRange() ) ) {
				return true;
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
		
		return SpawnBlockerNBTHelper.serializeSpawnBlockers( spawnBlockers );
	}
	
	@Override
	public void deserializeNBT( ListNBT nbt ) {
		
		spawnBlockers = SpawnBlockerNBTHelper.deserializeSpawnBlockers( nbt, SPAWN_BLOCKING_REGISTERY );
	}
	
	public void addSpawnBlocker( SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.add( spawnBlocker );
	}
	
	public void removeSpawnBlocker( SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.remove( spawnBlocker );
	}
}
