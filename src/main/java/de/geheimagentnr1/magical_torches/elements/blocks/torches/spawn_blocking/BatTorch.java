package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.BatTorchSpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class BatTorch extends SpawnBlockingTorch implements BlockRenderTypeInterface {
	
	
	public static final String registry_name = "bat_torch";
	
	private static final VoxelShape STANDING_SHAPE = Shapes.or(
		Block.box( 5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D ),
		Block.box( 6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D )
	);
	
	private static final VoxelShape HANGING_SHAPE = Shapes.or(
		Block.box( 5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D ),
		Block.box( 6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D )
	);
	
	public BatTorch() {
		
		super(
			Properties.of( Material.METAL ).strength( 3.5F ).sound( SoundType.LANTERN ),
			registry_name,
			BatTorchSpawnBlocker.registry_name,
			BatTorchSpawnBlocker::new
		);
		registerDefaultState( defaultBlockState().setValue( BlockStateProperties.HANGING, false ) );
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.cutout();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape(
		@Nonnull BlockState state,
		@Nonnull BlockGetter level,
		@Nonnull BlockPos pos,
		@Nonnull CollisionContext context ) {
		
		return state.getValue( BlockStateProperties.HANGING ) ? HANGING_SHAPE : STANDING_SHAPE;
	}
	
	@Override
	protected MutableComponent getInformation() {
		
		return new TranslatableComponent(
			TranslationKeyHelper.buildTooltipTranslationKey( "spawn_blocking_bat" ),
			ServerConfig.getAloneTorchRange()
		);
	}
	
	private static Direction hangingToDirection( BlockState state ) {
		
		return state.getValue( BlockStateProperties.HANGING ) ? Direction.DOWN : Direction.UP;
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement( @Nonnull BlockPlaceContext context ) {
		
		for( Direction direction : context.getNearestLookingDirections() ) {
			if( direction.getAxis() == Direction.Axis.Y ) {
				BlockState blockstate = defaultBlockState().setValue(
					BlockStateProperties.HANGING,
					direction == Direction.UP
				);
				if( blockstate.canSurvive( context.getLevel(), context.getClickedPos() ) ) {
					return blockstate;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public BlockState updateShape(
		@Nonnull BlockState state,
		@Nonnull Direction facing,
		@Nonnull BlockState facingState,
		@Nonnull LevelAccessor level,
		@Nonnull BlockPos currentPos,
		@Nonnull BlockPos facingPos ) {
		
		return hangingToDirection( state ).getOpposite() == facing && !state.canSurvive( level, currentPos )
			? Blocks.AIR.defaultBlockState()
			: super.updateShape( state, facing, facingState, level, currentPos, facingPos );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public boolean canSurvive( @Nonnull BlockState state, @Nonnull LevelReader level, @Nonnull BlockPos pos ) {
		
		Direction direction = hangingToDirection( state ).getOpposite();
		return Block.canSupportCenter( level, pos.relative( direction ), direction.getOpposite() );
	}
	
	@Override
	protected void createBlockStateDefinition( @Nonnull StateDefinition.Builder<Block, BlockState> builder ) {
		
		builder.add( BlockStateProperties.HANGING );
	}
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.BAT_TORCH, properties, registry_name );
	}
}
