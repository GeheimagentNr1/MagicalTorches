package de.geheimagentnr1.magical_torches.elements.item_groups;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;


public class MagicalTorchesItemGroup extends CreativeModeTab {
	
	
	//package-private
	MagicalTorchesItemGroup() {
		
		super( MagicalTorches.MODID );
	}
	
	@Nonnull
	@Override
	public ItemStack makeIcon() {
		
		return new ItemStack( ModBlocks.MEGA_TORCH );
	}
}
