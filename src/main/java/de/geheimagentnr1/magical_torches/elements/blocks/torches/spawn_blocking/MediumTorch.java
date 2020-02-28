package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.MediumTorchSpawnBlocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;


public class MediumTorch extends WoodenSpawnBlockingTorch {
	
	
	public final static String registry_name = "medium_torch";
	
	private final static VoxelShape SHAPE = Block.makeCuboidShape( 6.5, 0, 6.5, 9.5, 11, 9.5 );
	
	public MediumTorch() {
		
		super( registry_name, MediumTorchSpawnBlocker.registry_name, MediumTorchSpawnBlocker::new );
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape( BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context ) {
		
		return SHAPE;
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.MEDIUM_TORCH, properties, registry_name );
	}
}
