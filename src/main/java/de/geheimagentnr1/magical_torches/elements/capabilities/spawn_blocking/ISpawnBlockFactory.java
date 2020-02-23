package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking;

import net.minecraft.util.math.BlockPos;


@FunctionalInterface
public interface ISpawnBlockFactory {
	
	
	SpawnBlocker buildSpawnBlocker( BlockPos pos );
}
