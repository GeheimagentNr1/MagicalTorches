package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;


public class SoundMufflerHelper {
	
	
	public static TreeMap<RegistryKey<World>, TreeSet<SoundMuffler>> buildDimensionSoundMufflersTreeMap() {
		
		return new TreeMap<>( Comparator.naturalOrder() );
	}
	
	public static TreeSet<SoundMuffler> buildSoundMufflersTreeSet() {
		
		return new TreeSet<>( Comparator.comparing( SoundMuffler::getPos ) );
	}
}
