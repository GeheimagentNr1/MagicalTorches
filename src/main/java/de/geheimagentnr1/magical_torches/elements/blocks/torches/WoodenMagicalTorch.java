package de.geheimagentnr1.magical_torches.elements.blocks.torches;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;


//package-private
abstract class WoodenMagicalTorch extends MagicalTorch {
	
	
	//package-private
	WoodenMagicalTorch( String registry_name, ResourceLocation spawn_block_registry_name,
		ISpawnBlockFactory _spawnBlockFactory ) {
		
		super( Block.Properties.create( Material.WOOD ).hardnessAndResistance( 3.0F ).sound( SoundType.WOOD ),
			registry_name, spawn_block_registry_name, _spawnBlockFactory );
	}
}
