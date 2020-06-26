package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;


//package-private
abstract class WoodenSpawnBlockingTorch extends SpawnBlockingTorch {
	
	
	//package-private
	WoodenSpawnBlockingTorch( String registry_name, ResourceLocation spawn_block_registry_name,
		ISpawnBlockFactory _spawnBlockFactory ) {
		
		super( AbstractBlock.Properties.create( Material.WOOD ).hardnessAndResistance( 3 ).sound( SoundType.WOOD ),
			registry_name, spawn_block_registry_name, _spawnBlockFactory );
	}
}
