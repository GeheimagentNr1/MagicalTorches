package de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerHelper;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;
import java.util.TreeSet;


@RequiredArgsConstructor
public class ChickenEggSpawningCapability implements ValueIOSerializable {
	
	
	@NotNull
	public static final String registry_name = "chicken_egg_spawing";
	
	@NotNull
	private final ServerConfig serverConfig;
	
	@NotNull
	private TreeSet<SpawnBlocker> spawnBlockers = SpawnBlockerHelper.buildSpawnBlockerTreeSet();
	
	@NotNull
	private static final TreeMap<Identifier, ICapabilityDataFactory<SpawnBlocker>> SPAWN_BLOCKING_REGISTERY =
		new TreeMap<>();
	
	public static void registerChickenEggBlocker(
		@NotNull Identifier _registry_name,
		@NotNull ISpawnBlockerFactory factory ) {
		
		SPAWN_BLOCKING_REGISTERY.put( _registry_name, factory );
	}
	
	public boolean shouldBlockChickenEggSpawn( @NotNull Entity entity ) {
		
		if( entity instanceof ItemEntity && ( (ItemEntity)entity ).getItem().getItem() == Items.EGG ) {
			BlockPos spawn_pos = entity.blockPosition();
			boolean block = false;
			for( SpawnBlocker spawnBlocker : spawnBlockers ) {
				if( spawnBlocker.shouldBlockEntity( entity ) &&
					RadiusHelper.isEventInRadiusOfBlock( spawn_pos, spawnBlocker.getPos(), spawnBlocker.getRange() ) ) {
					block = true;
					break;
				}
			}
			if( serverConfig.getShouldInvertChickenEggBlocking() ) {
				block = !block;
			}
			return block;
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
