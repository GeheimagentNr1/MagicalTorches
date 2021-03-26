package de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;


public class ChickenEggSpawningCapabilityStorage implements Capability.IStorage<ChickenEggSpawningCapability> {
	
	
	@Nullable
	@Override
	public INBT writeNBT(
		Capability<ChickenEggSpawningCapability> capability,
		ChickenEggSpawningCapability instance,
		Direction side ) {
		
		return instance.serializeNBT();
	}
	
	@Override
	public void readNBT(
		Capability<ChickenEggSpawningCapability> capability, ChickenEggSpawningCapability instance,
		Direction side, INBT nbt ) {
		
		if( nbt instanceof ListNBT ) {
			instance.deserializeNBT( (ListNBT)nbt );
		}
	}
}
