package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public abstract class SpawnBlocker {
	
	
	private final BlockPos pos;
	
	protected SpawnBlocker( BlockPos _pos ) {
		
		pos = _pos;
	}
	
	public abstract ResourceLocation getRegistryName();
	
	public BlockPos getPos() {
		
		return pos;
	}
	
	public abstract int getRange();
}
