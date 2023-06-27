package de.geheimagentnr1.magical_torches.elements.capabilities;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public abstract class CapabilityData {
	
	
	@NotNull
	private final BlockPos pos;
	
	protected CapabilityData( @NotNull BlockPos _pos ) {
		
		pos = _pos;
	}
	
	@NotNull
	public abstract ResourceLocation getRegistryName();
	
	@NotNull
	public BlockPos getPos() {
		
		return pos;
	}
}
