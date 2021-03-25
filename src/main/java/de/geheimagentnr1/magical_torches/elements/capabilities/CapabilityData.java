package de.geheimagentnr1.magical_torches.elements.capabilities;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public abstract class CapabilityData {
	
	
	private final BlockPos pos;
	
	protected CapabilityData( BlockPos _pos ) {
		
		pos = _pos;
	}
	
	public abstract ResourceLocation getRegistryName();
	
	public BlockPos getPos() {
		
		return pos;
	}
}
