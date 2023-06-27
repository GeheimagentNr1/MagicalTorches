package de.geheimagentnr1.magical_torches.elements.capabilities;


import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;


@FunctionalInterface
public interface ICapabilityDataFactory<T extends CapabilityData> {
	
	
	//public
	T build( @NotNull BlockPos pos );
}
