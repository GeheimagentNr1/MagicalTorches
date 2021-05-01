package de.geheimagentnr1.magical_torches.config;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.world.dimension.DimensionType;

import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflersHolder {
	
	
	private static TreeMap<DimensionType, TreeSet<SoundMuffler>> DIMENSION_SOUND_MUFFLERS =
		SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	
	public static Optional<TreeSet<SoundMuffler>> getDimensionSoundMufflers( DimensionType dimension ) {
		
		return Optional.ofNullable( DIMENSION_SOUND_MUFFLERS.get( dimension ) );
	}
	
	public static TreeMap<DimensionType, TreeSet<SoundMuffler>> getDimensionSoundMufflers() {
		
		return DIMENSION_SOUND_MUFFLERS;
	}
	
	public static void setDimensionSoundMufflers( TreeMap<DimensionType, TreeSet<SoundMuffler>> _dimensionSoundMufflers ) {
		
		DIMENSION_SOUND_MUFFLERS = _dimensionSoundMufflers;
	}
	
	public static void clear() {
		
		DIMENSION_SOUND_MUFFLERS = SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
	}
}
