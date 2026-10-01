package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;


public class NBTHelper {
	
	
	@NotNull
	private static final String registryNameName = "registry_name";
	
	@NotNull
	private static final String xName = "x";
	
	@NotNull
	private static final String yName = "y";
	
	@NotNull
	private static final String zName = "z";
	
	@NotNull
	public static <T extends CapabilityData> ListTag serialize( @NotNull TreeSet<T> capabilityDatas ) {
		
		ListTag nbt = new ListTag();
		
		for( T capabilityData : capabilityDatas ) {
			BlockPos pos = capabilityData.getPos();
			CompoundTag compoundNBT = new CompoundTag();
			compoundNBT.putString( registryNameName, capabilityData.getRegistryName().toString() );
			compoundNBT.putInt( xName, pos.getX() );
			compoundNBT.putInt( yName, pos.getY() );
			compoundNBT.putInt( zName, pos.getZ() );
			nbt.add( compoundNBT );
		}
		return nbt;
	}
	
	@NotNull
	public static <T extends CapabilityData> TreeSet<T> deserialize(
		@NotNull ListTag nbt,
		@NotNull TreeMap<ResourceLocation, ICapabilityDataFactory<T>> capabilityDataRegistery ) {
		
		TreeSet<T> capabilityDatas = new TreeSet<>( Comparator.comparing( T::getPos ) );
		for( Tag inbt : nbt ) {
			if( inbt instanceof CompoundTag compoundNBT ) {
				Optional<ResourceLocation> registry_name = compoundNBT.getString( registryNameName )
					.map( ResourceLocation::tryParse );
				Optional<Integer> x = compoundNBT.getInt( xName );
				Optional<Integer> y = compoundNBT.getInt( yName );
				Optional<Integer> z = compoundNBT.getInt( zName );
				if( registry_name.isPresent() && x.isPresent() && y.isPresent() && z.isPresent() ) {
					ICapabilityDataFactory<T> factory = capabilityDataRegistery.get( registry_name.get() );
					if( factory != null ) {
						capabilityDatas.add( factory.build( new BlockPos( x.get(), y.get(), z.get() ) ) );
					}
				}
			}
		}
		return capabilityDatas;
	}
}
