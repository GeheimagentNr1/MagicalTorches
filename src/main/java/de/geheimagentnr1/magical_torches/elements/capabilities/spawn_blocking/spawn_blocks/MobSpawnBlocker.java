package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blocks;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.util.math.BlockPos;


public abstract class MobSpawnBlocker extends SpawnBlocker {
	
	
	protected MobSpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public boolean shouldBlockEntity( Entity entity ) {
		
		return entity instanceof MonsterEntity;
	}
}
