package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


@FunctionalInterface
public interface BlockItemInterface {
	
	
	//public
	Item getBlockItem( Item.Properties properties );
	
	//public
	default Item createBlockItem( Block block, Item.Properties properties, String registry_name ) {
		
		return new BlockItem( block, properties ).setRegistryName( registry_name );
	}
}
