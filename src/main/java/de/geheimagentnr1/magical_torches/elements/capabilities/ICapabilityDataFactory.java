package de.geheimagentnr1.magical_torches.elements.capabilities;


import net.minecraft.core.BlockPos;


@FunctionalInterface
public interface ICapabilityDataFactory<T extends CapabilityData> {
	
	
	//public
	T build( BlockPos pos );
}
