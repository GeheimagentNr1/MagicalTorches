package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerHelper;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.TreeMap;
import java.util.TreeSet;


@RequiredArgsConstructor
public class SpawnBlockingCapability implements ICapabilitySerializable<ListTag> {
	
	
	@NotNull
	public static final String registry_name = "spawn_blocking";
	
	@NotNull
	private final LazyOptional<SpawnBlockingCapability> capability = LazyOptional.of( () -> this );
	
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
	
	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability( @NotNull Capability<T> cap, @Nullable Direction side ) {
		
		if( cap == ModCapabilitiesRegisterFactory.SPAWN_BLOCKING ) {
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
