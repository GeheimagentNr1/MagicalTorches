package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;


public abstract class SpawnBlocker extends CapabilityData {
	
	
	protected SpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	public abstract int getRange();
	
	public abstract boolean shouldBlockEntity( Entity entity );
}
