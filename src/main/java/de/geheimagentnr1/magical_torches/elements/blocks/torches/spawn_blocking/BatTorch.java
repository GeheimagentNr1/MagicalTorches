package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.BatTorchSpawnBlocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class BatTorch extends SpawnBlockingTorch {
	
	
	public final static String registry_name = "bat_torch";
	
	private static final VoxelShape STANDING_SHAPE = VoxelShapes.or(
		Block.makeCuboidShape( 5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D ),
		Block.makeCuboidShape( 6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D ) );
	
	private static final VoxelShape HANGING_SHAPE = VoxelShapes.or(
		Block.makeCuboidShape( 5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D ),
		Block.makeCuboidShape( 6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D ) );
	
	public BatTorch() {
		
		super( Block.Properties.create( Material.IRON ).hardnessAndResistance( 3.5F ).sound( SoundType.LANTERN ),
			registry_name, BatTorchSpawnBlocker.registry_name, BatTorchSpawnBlocker::new );
		setDefaultState( stateContainer.getBaseState().with( BlockStateProperties.HANGING, Boolean.FALSE ) );
	}
	
	@Nonnull
	@Override
	public BlockRenderLayer getRenderLayer() {
		
		return BlockRenderLayer.CUTOUT;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape( BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos,
		@Nonnull ISelectionContext context ) {
		
		return state.get( BlockStateProperties.HANGING ) ? HANGING_SHAPE : STANDING_SHAPE;
	}
	
	private static Direction hangingToDirection( BlockState state ) {
		
		return state.get( BlockStateProperties.HANGING ) ? Direction.DOWN : Direction.UP;
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement( BlockItemUseContext context ) {
		
		for( Direction direction : context.getNearestLookingDirections() ) {
			if( direction.getAxis() == Direction.Axis.Y ) {
				BlockState blockstate = getDefaultState().with( BlockStateProperties.HANGING,
					direction == Direction.UP );
				if( blockstate.isValidPosition( context.getWorld(), context.getPos() ) ) {
					return blockstate;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public BlockState updatePostPlacement( @Nonnull BlockState stateIn, @Nonnull Direction facing,
		@Nonnull BlockState facingState,
		@Nonnull IWorld worldIn, @Nonnull BlockPos currentPos, @Nonnull BlockPos facingPos ) {
		
		return hangingToDirection( stateIn ).getOpposite() == facing && !stateIn.isValidPosition( worldIn,
			currentPos ) ?
			Blocks.AIR.getDefaultState() : super.updatePostPlacement( stateIn, facing, facingState, worldIn,
			currentPos, facingPos );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public boolean isValidPosition( @Nonnull BlockState state, @Nonnull IWorldReader worldIn, BlockPos pos ) {
		
		Direction direction = hangingToDirection( state ).getOpposite();
		return Block.hasEnoughSolidSide( worldIn, pos.offset( direction ), direction.getOpposite() );
	}
	
	@Override
	protected void fillStateContainer( StateContainer.Builder<Block, BlockState> builder ) {
		
		builder.add( BlockStateProperties.HANGING );
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.BAT_TORCH, properties, registry_name );
	}
}
