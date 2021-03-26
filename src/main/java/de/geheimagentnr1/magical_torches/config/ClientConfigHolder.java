package de.geheimagentnr1.magical_torches.config;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;


public class ClientConfigHolder {
	
	
	private static List<SoundCategory> SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS =
		ServerConfig.getDefaultSoundMufflingTorchToMuffleSounds();
	
	private static int SOUND_MUFFLING_TORCH_RANGE = ServerConfig.getDefaultSoundMufflingTorchRange();
	
	private static TreeMap<RegistryKey<World>, TreeSet<SoundMuffler>> DIMENSION_SOUND_MUFFLERS =
		SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	
	public static boolean doesSoundMufflingTorchToMuffleSoundsContainsSound( ISound sound ) {
		
		return SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS.contains( sound.getSource() );
	}
	
	public static List<SoundCategory> getSoundMufflingTorchToMuffleSounds() {
		
		return SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS;
	}
	
	public static void setSoundMufflingTorchToMuffleSounds( List<SoundCategory> _soundMufflingTorchToMuffleSounds ) {
		
		SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS = _soundMufflingTorchToMuffleSounds;
	}
	
	public static int getSoundMufflingTorchRange() {
		
		return SOUND_MUFFLING_TORCH_RANGE;
	}
	
	public static void setSoundMufflingTorchRange( int _soundMufflingTorchRange ) {
		
		SOUND_MUFFLING_TORCH_RANGE = _soundMufflingTorchRange;
	}
	
	public static Optional<TreeSet<SoundMuffler>> getDimensionSoundMufflers( RegistryKey<World> dimension ) {
		
		return Optional.ofNullable( DIMENSION_SOUND_MUFFLERS.get( dimension ) );
	}
	
	public static TreeMap<RegistryKey<World>, TreeSet<SoundMuffler>> getDimensionSoundMufflers() {
		
		return DIMENSION_SOUND_MUFFLERS;
	}
	
	public static void setDimensionSoundMufflers(
		TreeMap<RegistryKey<World>, TreeSet<SoundMuffler>> _dimensionSoundMufflers ) {
		
		DIMENSION_SOUND_MUFFLERS = _dimensionSoundMufflers;
	}
	
	public static void clear() {
		
		SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS = ServerConfig.getDefaultSoundMufflingTorchToMuffleSounds();
		SOUND_MUFFLING_TORCH_RANGE = ServerConfig.getDefaultSoundMufflingTorchRange();
		DIMENSION_SOUND_MUFFLERS = SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	}
}
