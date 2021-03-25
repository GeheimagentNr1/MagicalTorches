package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public abstract class HostileSpawnBlockingTorch extends SpawnBlockingTorch implements IWaterLoggable {
	
	
	//package-private
	HostileSpawnBlockingTorch(
		String registry_name,
		ResourceLocation spawn_block_registry_name,
		ISpawnBlockerFactory _spawnBlockFactory ) {
		
		super(
			Block.Properties.create( Material.WOOD ).hardnessAndResistance( 3 ).sound( SoundType.WOOD ),
			registry_name,
			spawn_block_registry_name,
			_spawnBlockFactory
		);
		setDefaultState( getDefaultState().with( BlockStateProperties.WATERLOGGED, false ) );
	}
	
	@Override
	protected TextComponent getInformation() {
		
		return new TranslationTextComponent(
			TranslationKeyHelper.buildTooltipTranslationKey( "spawn_blocking_hostile" ),
			spawnBlockFactory.build( BlockPos.ZERO ).getRange()
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public IFluidState getFluidState( @Nonnull BlockState state ) {
		
		return state.get( BlockStateProperties.WATERLOGGED )
			? Fluids.WATER.getStillFluidState( false )
			: super.getFluidState( state );
	}
	
	@Nullable
	public BlockState getStateForPlacement( @Nonnull BlockItemUseContext context ) {
		
		BlockPos pos = context.getPos();
		BlockState state = context.getWorld().getBlockState( pos );
		if( state.getBlock() == this ) {
			return state.with( BlockStateProperties.WATERLOGGED, false );
		} else {
			IFluidState ifluidstate = context.getWorld().getFluidState( pos );
			return getDefaultState().with( BlockStateProperties.WATERLOGGED, ifluidstate.getFluid() == Fluids.WATER );
		}
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public BlockState updatePostPlacement(
		@Nonnull BlockState stateIn,
		@Nonnull Direction facing,
		@Nonnull BlockState facingState,
		@Nonnull IWorld worldIn,
		@Nonnull BlockPos currentPos,
		@Nonnull BlockPos facingPos ) {
		
		if( stateIn.get( BlockStateProperties.WATERLOGGED ) ) {
			worldIn.getPendingFluidTicks().scheduleTick(
				currentPos,
				Fluids.WATER,
				Fluids.WATER.getTickRate( worldIn )
			);
		}
		return stateIn;
	}
	
	@Override
	protected void fillStateContainer( @Nonnull StateContainer.Builder<Block, BlockState> builder ) {
		
		builder.add( BlockStateProperties.WATERLOGGED );
	}
}
