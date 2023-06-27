package de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerHelper;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.TreeMap;
import java.util.TreeSet;


@RequiredArgsConstructor
public class ChickenEggSpawningCapability implements ICapabilitySerializable<ListTag> {
	
	
	@NotNull
	public static final String registry_name = "chicken_egg_spawing";
	
	@NotNull
	private final LazyOptional<ChickenEggSpawningCapability> capability = LazyOptional.of( () -> this );
	
	@NotNull
	private final ServerConfig serverConfig;
	
	@NotNull
	private TreeSet<SpawnBlocker> spawnBlockers = SpawnBlockerHelper.buildSpawnBlockerTreeSet();
	
	@NotNull
	private static final TreeMap<ResourceLocation, ICapabilityDataFactory<SpawnBlocker>> SPAWN_BLOCKING_REGISTERY =
		new TreeMap<>();
	
	public static void registerChickenEggBlocker(
		@NotNull ResourceLocation _registry_name,
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
	
	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability( @NotNull Capability<T> cap, @Nullable Direction side ) {
		
		if( cap == ModCapabilitiesRegisterFactory.CHICKEN_EGG_SPAWNING ) {
			return capability.cast();
		}
		return LazyOptional.empty();
	}
	
	@NotNull
	@Override
	public ListTag serializeNBT() {
		
		return NBTHelper.serialize( spawnBlockers );
	}
	
	@Override
	public void deserializeNBT( @NotNull ListTag nbt ) {
		
		spawnBlockers = NBTHelper.deserialize( nbt, SPAWN_BLOCKING_REGISTERY );
	}
	
	public void addSpawnBlocker( @NotNull SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.add( spawnBlocker );
	}
	
	public void removeSpawnBlocker( @NotNull SpawnBlocker spawnBlocker ) {
		
		spawnBlockers.remove( spawnBlocker );
	}
}
