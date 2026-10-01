package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;
import java.util.TreeSet;


public class SpawnBlockingCapability implements ValueIOSerializable {
	
	
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
	public void serialize( @NotNull ValueOutput output ) {
		
		NBTHelper.serialize( spawnBlockers, output );
	}
	
	@Override
	public void deserialize( @NotNull ValueInput input ) {
		
		spawnBlockers = NBTHelper.deserialize( input, SPAWN_BLOCKING_REGISTERY );
	}
	
	//Format up to 1.21.5 (INBTSerializable<ListTag>), see LegacyAttachmentMigrationHandler
	public void deserializeLegacy( @NotNull ListTag nbt ) {
		
		spawnBlockers = NBTHelper.deserialize( nbt, SPAWN_BLOCKING_REGISTERY );
	}
	
	public void addSpawnBlocker( @NotNull SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.add( spawnBlocker );
	}
	
	public void removeSpawnBlocker( @NotNull SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.remove( spawnBlocker );
	}
}
