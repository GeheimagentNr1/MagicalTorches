package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class NBTHelper {
	
	
	private static final String registryNameName = "registry_name";
	
	private static final String xName = "x";
	
	private static final String yName = "y";
	
	private static final String zName = "z";
	
	public static <T extends CapabilityData> ListTag serialize( TreeSet<T> capabilityDatas ) {
		
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
	
	public static <T extends CapabilityData> TreeSet<T> deserialize(
		ListTag nbt,
		TreeMap<ResourceLocation, ICapabilityDataFactory<T>> capabilityDataRegistery ) {
		
		TreeSet<T> capabilityDatas = new TreeSet<>( Comparator.comparing( T::getPos ) );
		for( Tag inbt : nbt ) {
			if( inbt instanceof CompoundTag compoundNBT ) {
				if( compoundNBT.contains( registryNameName, Tag.TAG_STRING ) ) {
					String registry_name_string = compoundNBT.getString( registryNameName );
					ResourceLocation registry_name = ResourceLocation.tryParse( registry_name_string );
					if( registry_name != null &&
						compoundNBT.contains( xName, Tag.TAG_INT ) &&
						compoundNBT.contains( yName, Tag.TAG_INT ) &&
						compoundNBT.contains( zName, Tag.TAG_INT ) ) {
						BlockPos pos = new BlockPos(
							compoundNBT.getInt( xName ),
							compoundNBT.getInt( yName ),
							compoundNBT.getInt( zName )
						);
						ICapabilityDataFactory<T> factory = capabilityDataRegistery.get( registry_name );
						if( factory != null ) {
							capabilityDatas.add( factory.build( pos ) );
						}
					}
				}
			}
		}
		return capabilityDatas;
	}
}
