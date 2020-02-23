package de.geheimagentnr1.magical_torches.elements.blocks.torches;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blocks.MegaTorchSpawnBlocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;


public class MegaTorch extends MagicalTorch {
	
	
	public final static String registry_name = "mega_torch";
	
	private final static VoxelShape SHAPE = Block.makeCuboidShape( 6, 0, 6, 10, 13, 10 );
	
	public MegaTorch() {
		
		super( registry_name, MegaTorchSpawnBlocker.registry_name, MegaTorchSpawnBlocker::new );
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape( BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context ) {
		
		return SHAPE;
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.MEGA_TORCH, properties, registry_name );
	}
}
