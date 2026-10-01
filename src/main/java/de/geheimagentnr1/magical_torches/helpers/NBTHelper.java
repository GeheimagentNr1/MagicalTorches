package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.CapabilityData;
import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;


public class NBTHelper {
	
	
	@NotNull
	private static final String listName = "entries";
	
	@NotNull
	private static final String registryNameName = "registry_name";
	
	@NotNull
	private static final String xName = "x";
	
	@NotNull
	private static final String yName = "y";
	
	@NotNull
	private static final String zName = "z";
	
	public static <T extends CapabilityData> void serialize(
		@NotNull TreeSet<T> capabilityDatas,
		@NotNull ValueOutput output ) {
		
		ValueOutput.ValueOutputList list = output.childrenList( listName );
		for( T capabilityData : capabilityDatas ) {
			BlockPos pos = capabilityData.getPos();
			ValueOutput entry = list.addChild();
			entry.putString( registryNameName, capabilityData.getRegistryName().toString() );
			entry.putInt( xName, pos.getX() );
			entry.putInt( yName, pos.getY() );
			entry.putInt( zName, pos.getZ() );
		}
	}
	
	@NotNull
	public static <T extends CapabilityData> TreeSet<T> deserialize(
		@NotNull ValueInput input,
		@NotNull TreeMap<Identifier, ICapabilityDataFactory<T>> capabilityDataRegistery ) {
		
		TreeSet<T> capabilityDatas = new TreeSet<>( Comparator.comparing( T::getPos ) );
		input.childrenListOrEmpty( listName ).forEach( entry -> {
			Optional<Identifier> registry_name = entry.getString( registryNameName )
				.map( Identifier::tryParse );
			Optional<Integer> x = entry.getInt( xName );
			Optional<Integer> y = entry.getInt( yName );
			Optional<Integer> z = entry.getInt( zName );
			if( registry_name.isPresent() && x.isPresent() && y.isPresent() && z.isPresent() ) {
				ICapabilityDataFactory<T> factory = capabilityDataRegistery.get( registry_name.get() );
				if( factory != null ) {
					capabilityDatas.add( factory.build( new BlockPos( x.get(), y.get(), z.get() ) ) );
				}
			}
		} );
		return capabilityDatas;
	}
	
	//Format up to 1.21.5, only read by the LegacyAttachmentMigrationHandler
	@NotNull
	public static <T extends CapabilityData> TreeSet<T> deserialize(
		@NotNull ListTag nbt,
		@NotNull TreeMap<Identifier, ICapabilityDataFactory<T>> capabilityDataRegistery ) {
		
		TreeSet<T> capabilityDatas = new TreeSet<>( Comparator.comparing( T::getPos ) );
		for( Tag inbt : nbt ) {
			if( inbt instanceof CompoundTag compoundNBT ) {
				Optional<Identifier> registry_name = compoundNBT.getString( registryNameName )
					.map( Identifier::tryParse );
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
