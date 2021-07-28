package de.geheimagentnr1.magical_torches.config;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflersHolder {
	
	
	private static TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> DIMENSION_SOUND_MUFFLERS =
		SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	
	public static Optional<TreeSet<SoundMuffler>> getDimensionSoundMufflers( ResourceKey<Level> dimension ) {
		
		return Optional.ofNullable( DIMENSION_SOUND_MUFFLERS.get( dimension ) );
	}
	
	public static TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> getDimensionSoundMufflers() {
		
		return DIMENSION_SOUND_MUFFLERS;
	}
	
	public static void setDimensionSoundMufflers(
		TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> _dimensionSoundMufflers ) {
		
		DIMENSION_SOUND_MUFFLERS = _dimensionSoundMufflers;
	}
	
	public static void clear() {
		
		DIMENSION_SOUND_MUFFLERS = SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	}
}
