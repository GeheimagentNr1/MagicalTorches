package de.geheimagentnr1.magical_torches.elements.capabilities;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;


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
