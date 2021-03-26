package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import de.geheimagentnr1.magical_torches.network.AddSoundMufflerMsg;
import de.geheimagentnr1.magical_torches.network.RemoveSoundMufflerMsg;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflingCapability implements ICapabilitySerializable<ListNBT> {
	
	
	public static final ResourceLocation registry_name = ResourceLocationBuilder.build( "sound_muffling" );
	
	private final LazyOptional<SoundMufflingCapability> capability = LazyOptional.of( () -> this );
	
	private TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
	
	private static final TreeMap<ResourceLocation, ICapabilityDataFactory<SoundMuffler>> SOUND_MUFFLING_REGISTERY =
		new TreeMap<>();
	
	public static void registerSoundMufflers( ResourceLocation _registry_name, ISoundMufflerFactory factory ) {
		
		SOUND_MUFFLING_REGISTERY.put( _registry_name, factory );
	}
	
	public static SoundMuffler buildSoundMuffler( ResourceLocation soundMufflerRegistryName, BlockPos pos ) {
		
		return SOUND_MUFFLING_REGISTERY.get( soundMufflerRegistryName ).build( pos );
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability( @Nonnull Capability<T> cap, @Nullable Direction side ) {
		
		if( cap == ModCapabilities.SOUND_MUFFLING ) {
			return capability.cast();
		}
		return LazyOptional.empty();
	}
	
	@Override
	public ListNBT serializeNBT() {
		
		return NBTHelper.serialize( soundMufflers );
	}
	
	@Override
	public void deserializeNBT( ListNBT nbt ) {
		
		soundMufflers = NBTHelper.deserialize( nbt, SOUND_MUFFLING_REGISTERY );
	}
	
	public void addSoundMuffler( RegistryKey<World> dimension, SoundMuffler soundMuffler ) {
		
		soundMufflers.add( soundMuffler );
		AddSoundMufflerMsg.sendToAll( dimension.func_240901_a_(), soundMuffler );
	}
	
	public void removeSoundMuffler( RegistryKey<World> dimension, SoundMuffler soundMuffler ) {
		
		soundMufflers.remove( soundMuffler );
		RemoveSoundMufflerMsg.sendToAll( dimension.func_240901_a_(), soundMuffler );
	}
	
	public TreeSet<SoundMuffler> getSoundMufflers() {
		
		return soundMufflers;
	}
}
