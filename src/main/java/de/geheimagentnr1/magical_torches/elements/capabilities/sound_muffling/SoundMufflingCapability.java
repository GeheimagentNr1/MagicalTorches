package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import de.geheimagentnr1.magical_torches.network.AddSoundMufflerMsg;
import de.geheimagentnr1.magical_torches.network.RemoveSoundMufflerMsg;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.TreeMap;
import java.util.TreeSet;


@RequiredArgsConstructor
public class SoundMufflingCapability implements ICapabilitySerializable<ListTag> {
	
	
	@NotNull
	public static final String registry_name = "sound_muffling";
	
	@NotNull
	private final LazyOptional<SoundMufflingCapability> capability = LazyOptional.of( () -> this );
	
	@NotNull
	private TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
	
	@NotNull
	private static final TreeMap<ResourceLocation, ICapabilityDataFactory<SoundMuffler>> SOUND_MUFFLING_REGISTERY =
		new TreeMap<>();
	
	public static void registerSoundMufflers(
		@NotNull ResourceLocation _registry_name,
		@NotNull ISoundMufflerFactory factory ) {
		
		SOUND_MUFFLING_REGISTERY.put( _registry_name, factory );
	}
	
	@NotNull
	public static SoundMuffler buildSoundMuffler(
		@NotNull ResourceLocation soundMufflerRegistryName,
		@NotNull BlockPos pos ) {
		
		return SOUND_MUFFLING_REGISTERY.get( soundMufflerRegistryName ).build( pos );
	}
	
	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability( @NotNull Capability<T> cap, @Nullable Direction side ) {
		
		if( cap == ModCapabilitiesRegisterFactory.SOUND_MUFFLING ) {
			return capability.cast();
		}
		return LazyOptional.empty();
	}
	
	@NotNull
	@Override
	public ListTag serializeNBT() {
		
		return NBTHelper.serialize( soundMufflers );
	}
	
	@Override
	public void deserializeNBT( @NotNull ListTag nbt ) {
		
		soundMufflers = NBTHelper.deserialize( nbt, SOUND_MUFFLING_REGISTERY );
	}
	
	public void addSoundMuffler( @NotNull ResourceKey<Level> dimension, @NotNull SoundMuffler soundMuffler ) {
		
		soundMufflers.add( soundMuffler );
		AddSoundMufflerMsg.sendToAll( dimension.location(), soundMuffler );
	}
	
	public void removeSoundMuffler( @NotNull ResourceKey<Level> dimension, @NotNull SoundMuffler soundMuffler ) {
		
		soundMufflers.remove( soundMuffler );
		RemoveSoundMufflerMsg.sendToAll( dimension.location(), soundMuffler );
	}
	
	@NotNull
	public TreeSet<SoundMuffler> getSoundMufflers() {
		
		return soundMufflers;
	}
}
