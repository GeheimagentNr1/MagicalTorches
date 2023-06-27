package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;


public abstract class SpawnBlocker extends CapabilityData {
	
	
	protected SpawnBlocker( @NotNull BlockPos _pos ) {
		
		super( _pos );
	}
	
	public abstract int getRange();
	
	public abstract boolean shouldBlockEntity( @NotNull Entity entity );
}
