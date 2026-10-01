package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import net.minecraft.world.item.BlockItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.jetbrains.annotations.NotNull;


public class BlockTooltipHandler {
	
	
	@SubscribeEvent
	public void handleItemTooltipEvent( @NotNull ItemTooltipEvent event ) {
		
		if( event.getItemStack().getItem() instanceof BlockItem blockItem &&
			blockItem.getBlock() instanceof BlockWithTooltip blockWithTooltip ) {
			event.getToolTip().add( blockWithTooltip.getStyledInformation() );
		}
	}
}
