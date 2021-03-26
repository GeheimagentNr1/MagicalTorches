package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class NBTHelper {
	
	
	private static final String registryNameName = "registry_name";
	
	private static final String xName = "x";
	
	private static final String yName = "y";
	
	private static final String zName = "z";
	
	public static <T extends CapabilityData> ListNBT serialize( TreeSet<T> capabilityDatas ) {
		
		ListNBT nbt = new ListNBT();
		
		for( T capabilityData : capabilityDatas ) {
			BlockPos pos = capabilityData.getPos();
			CompoundNBT compoundNBT = new CompoundNBT();
			compoundNBT.putString( registryNameName, capabilityData.getRegistryName().toString() );
			compoundNBT.putInt( xName, pos.getX() );
			compoundNBT.putInt( yName, pos.getY() );
			compoundNBT.putInt( zName, pos.getZ() );
			nbt.add( compoundNBT );
		}
		return nbt;
	}
	
	public static <T extends CapabilityData> TreeSet<T> deserialize(
		ListNBT nbt,
		TreeMap<ResourceLocation, ICapabilityDataFactory<T>> capabilityDataRegistery ) {
		
		TreeSet<T> capabilityDatas = new TreeSet<>( Comparator.comparing( T::getPos ) );
		for( INBT inbt : nbt ) {
			if( inbt instanceof CompoundNBT ) {
				CompoundNBT compoundNBT = (CompoundNBT)inbt;
				if( compoundNBT.contains( registryNameName, Constants.NBT.TAG_STRING ) ) {
					String registry_name_string = compoundNBT.getString( registryNameName );
					ResourceLocation registry_name = ResourceLocation.tryCreate( registry_name_string );
					if( registry_name != null &&
						compoundNBT.contains( xName, Constants.NBT.TAG_INT ) &&
						compoundNBT.contains( yName, Constants.NBT.TAG_INT ) &&
						compoundNBT.contains( zName, Constants.NBT.TAG_INT ) ) {
						BlockPos pos = new BlockPos( new BlockPos( compoundNBT.getInt( xName ),
							compoundNBT.getInt( yName ), compoundNBT.getInt( zName )
						) );
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
