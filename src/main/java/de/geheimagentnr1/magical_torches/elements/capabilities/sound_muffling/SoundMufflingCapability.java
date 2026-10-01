package de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.capabilities.ICapabilityDataFactory;
import de.geheimagentnr1.magical_torches.helpers.NBTHelper;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import de.geheimagentnr1.magical_torches.network.AddSoundMufflerMsg;
import de.geheimagentnr1.magical_torches.network.RemoveSoundMufflerMsg;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.ValueIOSerializable;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;

import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflingCapability implements ValueIOSerializable {
	
	
	@NotNull
	public static final String registry_name = "sound_muffling";
	
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
	
	@Override
	public void serialize( @NotNull ValueOutput output ) {
		
		NBTHelper.serialize( soundMufflers, output );
	}
	
	@Override
	public void deserialize( @NotNull ValueInput input ) {
		
		soundMufflers = NBTHelper.deserialize( input, SOUND_MUFFLING_REGISTERY );
	}
	
	//Format up to 1.21.5 (INBTSerializable<ListTag>), see LegacyAttachmentMigrationHandler
	public void deserializeLegacy( @NotNull ListTag nbt ) {
		
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
