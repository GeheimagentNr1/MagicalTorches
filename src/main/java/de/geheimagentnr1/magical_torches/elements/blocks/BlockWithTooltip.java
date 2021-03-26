package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;


@SuppressWarnings( "AbstractClassExtendsConcreteClass" )
public abstract class BlockWithTooltip extends Block {
	
	
	protected BlockWithTooltip( Properties properties ) {
		
		super( properties );
	}
	
	@OnlyIn( Dist.CLIENT )
	@Override
	public void addInformation(
		@Nonnull ItemStack stack,
		@Nullable IBlockReader worldIn,
		@Nonnull List<ITextComponent> tooltip,
		@Nonnull ITooltipFlag flagIn ) {
		
		tooltip.add( getInformation().func_230530_a_( Style.field_240709_b_.func_240720_a_(
			TextFormatting.ITALIC,
			TextFormatting.GRAY
		) ) );
	}
	
	protected abstract TextComponent getInformation();
}
