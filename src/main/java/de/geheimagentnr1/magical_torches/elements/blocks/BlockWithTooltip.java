package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;


@SuppressWarnings( "AbstractClassExtendsConcreteClass" )
public abstract class BlockWithTooltip extends Block {
	
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	protected BlockWithTooltip( @NotNull Properties properties ) {
		
		super( properties );
	}
	
	@Override
	public void appendHoverText(
		@NotNull ItemStack stack,
		@Nullable BlockGetter level,
		@NotNull List<Component> tooltip,
		@NotNull TooltipFlag flag ) {
		
		tooltip.add( getInformation().setStyle(
			Style.EMPTY.applyFormats( ChatFormatting.ITALIC, ChatFormatting.GRAY )
		) );
	}
	
	@NotNull
	protected abstract MutableComponent getInformation();
}
