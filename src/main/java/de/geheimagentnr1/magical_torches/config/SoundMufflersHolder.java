package de.geheimagentnr1.magical_torches.config;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflersHolder {
	
	
	private static TreeMap<RegistryKey<World>, TreeSet<SoundMuffler>> DIMENSION_SOUND_MUFFLERS =
		SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	
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
		
		DIMENSION_SOUND_MUFFLERS = SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	}
}
