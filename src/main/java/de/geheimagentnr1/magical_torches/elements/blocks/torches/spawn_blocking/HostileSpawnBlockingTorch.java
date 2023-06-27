package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.minecraft_forge_api.util.TranslationKeyHelper;
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
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;


public abstract class HostileSpawnBlockingTorch extends SpawnBlockingTorch implements SimpleWaterloggedBlock {
	
	
	//package-private
	HostileSpawnBlockingTorch(
		@NotNull ResourceLocation spawn_block_registry_name,
		@NotNull ISpawnBlockerFactory _spawnBlockFactory ) {
		
		super(
			Properties.of().mapColor( MapColor.WOOD ).strength( 3 ).sound( SoundType.WOOD ),
			spawn_block_registry_name,
			_spawnBlockFactory
		);
		registerDefaultState( defaultBlockState().setValue( BlockStateProperties.WATERLOGGED, false ) );
	}
	
	@NotNull
	@Override
	protected MutableComponent getInformation() {
		
		return Component.translatable(
			TranslationKeyHelper.generateTooltipTranslationKey( MagicalTorches.MODID, "spawn_blocking_hostile" ),
			spawnBlockFactory.build( BlockPos.ZERO ).getRange()
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@NotNull
	@Override
	public FluidState getFluidState( @NotNull BlockState state ) {
		
		return state.getValue( BlockStateProperties.WATERLOGGED )
			? Fluids.WATER.getSource( false )
			: super.getFluidState( state );
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement( @NotNull BlockPlaceContext context ) {
		
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
	@NotNull
	@Override
	public BlockState updateShape(
		@NotNull BlockState state,
		@NotNull Direction facing,
		@NotNull BlockState facingState,
		@NotNull LevelAccessor level,
		@NotNull BlockPos currentPos,
		@NotNull BlockPos facingPos ) {
		
		if( state.getValue( BlockStateProperties.WATERLOGGED ) ) {
			level.scheduleTick( currentPos, Fluids.WATER, Fluids.WATER.getTickDelay( level ) );
		}
		return state;
	}
	
	@Override
	protected void createBlockStateDefinition( @NotNull StateDefinition.Builder<Block, BlockState> builder ) {
		
		builder.add( BlockStateProperties.WATERLOGGED );
	}
}
