package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;


@SuppressWarnings( "AbstractClassExtendsConcreteClass" )
public abstract class BlockWithTooltip extends Block {
	
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	protected BlockWithTooltip( @NotNull Properties properties ) {
		
		super( properties );
	}
	
	//Shown by BlockTooltipHandler (ItemTooltipEvent), appendHoverText changed its signature in 1.21.5
	@NotNull
	public MutableComponent getStyledInformation() {
		
		return getInformation().setStyle( Style.EMPTY.applyFormats( ChatFormatting.ITALIC, ChatFormatting.GRAY ) );
	}
	
	@NotNull
	protected abstract MutableComponent getInformation();
}
