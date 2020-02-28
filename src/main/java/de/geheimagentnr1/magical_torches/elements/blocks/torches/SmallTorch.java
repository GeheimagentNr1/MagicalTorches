package de.geheimagentnr1.magical_torches.elements.blocks.torches;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.SmallTorchSpawnBlocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;


public class SmallTorch extends WoodenSpawnBlockingTorch {
	
	
	public final static String registry_name = "small_torch";
	
	private final static VoxelShape SHAPE = Block.makeCuboidShape( 7, 0, 7, 9, 10, 9 );
	
	public SmallTorch() {
		
		super( registry_name, SmallTorchSpawnBlocker.registry_name, SmallTorchSpawnBlocker::new );
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape( BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context ) {
		
		return SHAPE;
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.SMALL_TORCH, properties, registry_name );
	}
}
