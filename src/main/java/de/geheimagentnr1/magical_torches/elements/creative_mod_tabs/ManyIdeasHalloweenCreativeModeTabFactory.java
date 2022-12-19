package de.geheimagentnr1.magical_torches.elements.creative_mod_tabs;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;


public class ManyIdeasHalloweenCreativeModeTabFactory implements CreativeModeTabFactory {
	
	
	@Override
	public String getModId() {
		
		return MagicalTorches.MODID;
	}
	
	@Override
	public String getRegistryName() {
		
		return MagicalTorches.MODID;
	}
	
	@Override
	public Item getDisplayItem() {
		
		return ModBlocks.MEGA_TORCH.asItem();
	}
	
	@Override
	public List<ItemStack> getDisplayItems() {
		
		return ModBlocks.BLOCKS.stream()
			.filter( registryEntry -> registryEntry.getValue() instanceof BlockItemInterface )
			.map( registryEntry -> new ItemStack( registryEntry.getValue() ) )
			.toList();
	}
}
