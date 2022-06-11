package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public abstract class HostileSpawnBlockingTorch extends SpawnBlockingTorch implements SimpleWaterloggedBlock {
	
	
	//package-private
	HostileSpawnBlockingTorch(
		String registry_name,
		ResourceLocation spawn_block_registry_name,
		ISpawnBlockerFactory _spawnBlockFactory ) {
		
		super(
			Properties.of( Material.WOOD ).strength( 3 ).sound( SoundType.WOOD ),
			spawn_block_registry_name,
			_spawnBlockFactory
		);
		registerDefaultState( defaultBlockState().setValue( BlockStateProperties.WATERLOGGED, false ) );
	}
	
	@Override
	protected MutableComponent getInformation() {
		
		return Component.translatable(
			TranslationKeyHelper.buildTooltipTranslationKey( "spawn_blocking_hostile" ),
			spawnBlockFactory.build( BlockPos.ZERO ).getRange()
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public FluidState getFluidState( @Nonnull BlockState state ) {
		
		return state.getValue( BlockStateProperties.WATERLOGGED )
			? Fluids.WATER.getSource( false )
			: super.getFluidState( state );
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement( @Nonnull BlockPlaceContext context ) {
		
		BlockPos pos = context.getClickedPos();
		BlockState state = context.getLevel().getBlockState( pos );
		if( state.getBlock() == this ) {
			return state.setValue( BlockStateProperties.WATERLOGGED, false );
		} else {
			FluidState fluidState = context.getLevel().getFluidState( pos );
			return defaultBlockState().setValue(
				BlockStateProperties.WATERLOGGED,
				fluidState.getType() == Fluids.WATER
			);
		}
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
		
		if( state.getValue( BlockStateProperties.WATERLOGGED ) ) {
			level.scheduleTick( currentPos, Fluids.WATER, Fluids.WATER.getTickDelay( level ) );
		}
		return state;
	}
	
	@Override
	protected void createBlockStateDefinition( @Nonnull StateDefinition.Builder<Block, BlockState> builder ) {
		
		builder.add( BlockStateProperties.WATERLOGGED );
	}
}
