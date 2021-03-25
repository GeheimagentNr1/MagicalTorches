package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;


public class SoundMufflingCapabilityStorage implements Capability.IStorage<SoundMufflingCapability> {
	
	
	@Nullable
	@Override
	public INBT writeNBT(
		Capability<SoundMufflingCapability> capability,
		SoundMufflingCapability instance,
		Direction side ) {
		
		return instance.serializeNBT();
	}
	
	@Override
	public void readNBT(
		Capability<SoundMufflingCapability> capability,
		SoundMufflingCapability instance,
		Direction side,
		INBT nbt ) {
		
		if( nbt instanceof ListNBT ) {
			instance.deserializeNBT( (ListNBT)nbt );
		}
	}
}
