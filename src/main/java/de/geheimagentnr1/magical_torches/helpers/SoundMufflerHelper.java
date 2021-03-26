package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import net.minecraft.world.dimension.DimensionType;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflerHelper {
	
	
	public static TreeMap<DimensionType, TreeSet<SoundMuffler>> buildDimensionSoundMufflersTreeMap() {
		
		return new TreeMap<>( Comparator.comparingInt( DimensionType::getId ) );
	}
	
	public static TreeSet<SoundMuffler> buildSoundMufflersTreeSet() {
		
		return new TreeSet<>( Comparator.comparing( SoundMuffler::getPos ) );
	}
}
