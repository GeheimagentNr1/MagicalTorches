package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public abstract class MobSpawnBlocker extends SpawnBlocker {
	
	
	//package-private
	MobSpawnBlocker( BlockPos _pos ) {
		
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
