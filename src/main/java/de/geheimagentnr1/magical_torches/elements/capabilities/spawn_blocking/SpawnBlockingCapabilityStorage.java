package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;


public class SpawnBlockingCapabilityStorage implements Capability.IStorage<SpawnBlockingCapability> {
	
	
	@Nullable
	@Override
	public INBT writeNBT(
		Capability<SpawnBlockingCapability> capability,
		SpawnBlockingCapability instance,
		Direction side ) {
		
		return instance.serializeNBT();
	}
	
	@Override
	public void readNBT(
		Capability<SpawnBlockingCapability> capability,
		SpawnBlockingCapability instance,
		Direction side,
		INBT nbt ) {
		
		if( nbt instanceof ListNBT ) {
			instance.deserializeNBT( (ListNBT)nbt );
		}
	}
}
