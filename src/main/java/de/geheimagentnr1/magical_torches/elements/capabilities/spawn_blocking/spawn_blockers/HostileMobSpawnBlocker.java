package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;


public abstract class HostileMobSpawnBlocker extends SpawnBlocker {
	
	
	//package-private
	HostileMobSpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public boolean shouldBlockEntity( Entity entity ) {
		
		for( ResourceLocation registryName : ServerConfig.getHostileBlockedEntities() ) {
			if( registryName.equals( entity.getType().getRegistryName() ) ) {
				return true;
			}
		}
		return false;
	}
}
