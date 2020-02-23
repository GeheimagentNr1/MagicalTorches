package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;


@SuppressWarnings( "InterfaceWithOnlyOneDirectInheritor" )
@FunctionalInterface
public interface BlockItemInterface {
	
	
	//public
	Item getBlockItem( Item.Properties properties );
	
	//public
	default Item createBlockItem( Block block, Item.Properties properties, String registry_name ) {
		
		return new BlockItem( block, properties ).setRegistryName( registry_name );
	}
}
