package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;
import java.util.TreeSet;


public class SpawnBlockingCapability implements INBTSerializable<ListTag> {
	
	
	@NotNull
	public static final String registry_name = "spawn_blocking";
	
	@NotNull
	private TreeSet<SpawnBlocker> spawnBlockers = SpawnBlockerHelper.buildSpawnBlockerTreeSet();
	
	@NotNull
	private static final TreeMap<ResourceLocation, ICapabilityDataFactory<SpawnBlocker>> SPAWN_BLOCKING_REGISTERY =
		new TreeMap<>();
	
	public static void registerSpawnBlocker(
		@NotNull ResourceLocation _registry_name,
		@NotNull ISpawnBlockerFactory factory ) {
		
		SPAWN_BLOCKING_REGISTERY.put( _registry_name, factory );
	}
	
	public boolean shouldBlockEntitySpawn( @NotNull Entity entity ) {
		
		BlockPos spawn_pos = entity.blockPosition();
		for( SpawnBlocker spawnBlocker : spawnBlockers ) {
			if( spawnBlocker.shouldBlockEntity( entity ) &&
				RadiusHelper.isEventInRadiusOfBlock( spawn_pos, spawnBlocker.getPos(), spawnBlocker.getRange() ) ) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public ListTag serializeNBT( HolderLookup.Provider provider ) {
		
		return NBTHelper.serialize( spawnBlockers );
	}
	
	@Override
	public void deserializeNBT( HolderLookup.Provider provider, ListTag nbt ) {
		
		spawnBlockers = NBTHelper.deserialize( nbt, SPAWN_BLOCKING_REGISTERY );
	}
	
	public void addSpawnBlocker( @NotNull SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.add( spawnBlocker );
	}
	
	public void removeSpawnBlocker( @NotNull SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.remove( spawnBlocker );
	}
}
