package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


@SuppressWarnings( "AbstractClassExtendsConcreteClass" )
public abstract class BlockWithTooltip extends Block {
	
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	protected BlockWithTooltip( Properties properties ) {
		
		super( properties );
	}
	
	@Override
	public void appendHoverText(
		@Nonnull ItemStack stack,
		@Nullable BlockGetter level,
		List<Component> tooltip,
		@Nonnull TooltipFlag flag ) {
		
		tooltip.add( getInformation().setStyle(
			Style.EMPTY.applyFormats( ChatFormatting.ITALIC, ChatFormatting.GRAY )
		) );
	}
	
	protected abstract MutableComponent getInformation();
}
