package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;


public abstract class HostileMobSpawnBlocker extends SpawnBlocker {
	
	
	HostileMobSpawnBlocker( @NotNull BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public boolean shouldBlockEntity( @NotNull Entity entity ) {
		
		for( ResourceLocation registryName : ServerConfig.getINSTANCE().getHostileBlockedEntities() ) {
			if( registryName.equals( BuiltInRegistries.ENTITY_TYPE.getKey( entity.getType() ) ) ) {
				return true;
			}
		}
		return false;
	}
}
