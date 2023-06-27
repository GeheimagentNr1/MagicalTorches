package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.BatTorchSpawnBlocker;
import de.geheimagentnr1.minecraft_forge_api.util.TranslationKeyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
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
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public class BatTorch extends SpawnBlockingTorch {
	
	
	@NotNull
	public static final String registry_name = "bat_torch";
	
	@NotNull
	private static final VoxelShape STANDING_SHAPE = Shapes.or(
		Block.box( 5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D ),
		Block.box( 6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D )
	);
	
	@NotNull
	private static final VoxelShape HANGING_SHAPE = Shapes.or(
		Block.box( 5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D ),
		Block.box( 6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D )
	);
	
	public BatTorch() {
		
		super(
			Properties.of()
				.mapColor( MapColor.METAL )
				.strength( 3.5F )
				.requiresCorrectToolForDrops()
				.sound( SoundType.LANTERN ),
			BatTorchSpawnBlocker.registry_name,
			BatTorchSpawnBlocker::new
		);
		registerDefaultState( defaultBlockState().setValue( BlockStateProperties.HANGING, false ) );
	}
	
	@SuppressWarnings( "deprecation" )
	@NotNull
	@Override
	public VoxelShape getShape(
		@NotNull BlockState state,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull CollisionContext context ) {
		
		return state.getValue( BlockStateProperties.HANGING ) ? HANGING_SHAPE : STANDING_SHAPE;
	}
	
	@NotNull
	@Override
	protected MutableComponent getInformation() {
		
		return Component.translatable(
			TranslationKeyHelper.generateTooltipTranslationKey( MagicalTorches.MODID, "spawn_blocking_bat" ),
			ServerConfig.getINSTANCE().getBatTorchRange()
		);
	}
	
	@NotNull
	private static Direction hangingToDirection( @NotNull BlockState state ) {
		
		return state.getValue( BlockStateProperties.HANGING ) ? Direction.DOWN : Direction.UP;
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement( @NotNull BlockPlaceContext context ) {
		
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
	@NotNull
	@Override
	public BlockState updateShape(
		@NotNull BlockState state,
		@NotNull Direction facing,
		@NotNull BlockState facingState,
		@NotNull LevelAccessor level,
		@NotNull BlockPos currentPos,
		@NotNull BlockPos facingPos ) {
		
		return hangingToDirection( state ).getOpposite() == facing && !state.canSurvive( level, currentPos )
			? Blocks.AIR.defaultBlockState()
			: super.updateShape( state, facing, facingState, level, currentPos, facingPos );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public boolean canSurvive( @NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos ) {
		
		Direction direction = hangingToDirection( state ).getOpposite();
		return Block.canSupportCenter( level, pos.relative( direction ), direction.getOpposite() );
	}
	
	@Override
	protected void createBlockStateDefinition( @NotNull StateDefinition.Builder<Block, BlockState> builder ) {
		
		builder.add( BlockStateProperties.HANGING );
	}
}
