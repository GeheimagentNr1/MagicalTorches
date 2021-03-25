package de.geheimagentnr1.magical_torches.elements.item_groups;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


public class MagicalTorchesItemGroup extends ItemGroup {
	
	
	//package-private
	MagicalTorchesItemGroup() {
		
		super( MagicalTorches.MODID );
	}
	
	@Nonnull
	@Override
	public ItemStack createIcon() {
		
		return new ItemStack( ModBlocks.MEGA_TORCH );
	}
}
