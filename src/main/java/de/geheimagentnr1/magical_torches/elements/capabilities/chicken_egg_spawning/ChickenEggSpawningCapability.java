package de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.config.MainConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import de.geheimagentnr1.magical_torches.helpers.SpawnBlockerNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
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


public class ChickenEggSpawningCapability implements ICapabilitySerializable<ListNBT> {
	
	
	public static final ResourceLocation registry_name = ResourceLocationBuilder.build( "chicken_egg_spawing" );
	
	private final LazyOptional<ChickenEggSpawningCapability> capability = LazyOptional.of( () -> this );
	
	private TreeSet<SpawnBlocker> spawnBlockers = new TreeSet<>( Comparator.comparing( SpawnBlocker::getPos ) );
	
	private static final TreeMap<ResourceLocation, ISpawnBlockFactory> SPAWN_BLOCKING_REGISTERY = new TreeMap<>();
	
	public static void registerChickenEggBlocker( ResourceLocation _registry_name, ISpawnBlockFactory factory ) {
		
		SPAWN_BLOCKING_REGISTERY.put( _registry_name, factory );
	}
	
	public boolean shouldBlockChickenEggSpawn( Entity entity ) {
		
		if( entity instanceof ItemEntity && ( (ItemEntity)entity ).getItem().getItem() == Items.EGG ) {
			BlockPos spawn_pos = entity.func_233580_cy_();
			boolean block = false;
			for( SpawnBlocker spawnBlocker : spawnBlockers ) {
				if( spawnBlocker.shouldBlockEntity( entity ) && RadiusHelper.isEventInRadiusOfBlock( spawn_pos,
					spawnBlocker.getPos(), spawnBlocker.getRange() ) ) {
					block = true;
					break;
				}
			}
			if( MainConfig.getShouldInvertChickenEggBlocking() ) {
				block = !block;
			}
			return block;
		}
		return false;
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability( @Nonnull Capability<T> cap, @Nullable Direction side ) {
		
		if( cap == ModCapabilities.CHICKEN_EGG_SPAWNING ) {
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
