package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.TreeSet;


public class SpawnBlockerHelper {
	
	
	@NotNull
	public static TreeSet<SpawnBlocker> buildSpawnBlockerTreeSet() {
		
		return new TreeSet<>( Comparator.comparing( SpawnBlocker::getPos ) );
	}
}
