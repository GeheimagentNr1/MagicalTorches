package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflerHelper {
	
	
	public static TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> buildDimensionSoundMufflersTreeMap() {
		
		return new TreeMap<>( Comparator.naturalOrder() );
	}
	
	public static TreeSet<SoundMuffler> buildSoundMufflersTreeSet() {
		
		return new TreeSet<>( Comparator.comparing( SoundMuffler::getPos ) );
	}
}
