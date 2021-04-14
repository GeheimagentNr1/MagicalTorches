package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


//package-private
abstract class SpawnBlockingTorch extends BlockWithTooltip implements BlockItemInterface {
	
	
	//package-private
	final ISpawnBlockerFactory spawnBlockFactory;
	
	//package-private
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	SpawnBlockingTorch(
		@Nonnull AbstractBlock.Properties properties,
		@Nonnull String registry_name,
		@Nonnull ResourceLocation spawn_block_registry_name,
		@Nonnull ISpawnBlockerFactory _spawnBlockFactory ) {
		
		super( properties.doesNotBlockMovement().setLightLevel( value -> 15 ) );
		setRegistryName( registry_name );
		spawnBlockFactory = _spawnBlockFactory;
		SpawnBlockingCapability.registerSpawnBlocker( spawn_block_registry_name, _spawnBlockFactory );
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape(
		@Nonnull BlockState state,
		@Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos,
		@Nonnull ISelectionContext context ) {
		
		return VoxelShapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public PushReaction getPushReaction( @Nonnull BlockState state ) {
		
		return PushReaction.DESTROY;
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onBlockAdded(
		@Nonnull BlockState state,
		@Nonnull World worldIn,
		@Nonnull BlockPos pos,
		@Nonnull BlockState oldState,
		boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.SPAWN_BLOCKING )
			.ifPresent( capability -> capability.addSpawnBlocker( spawnBlockFactory.build( pos ) ) );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onReplaced(
		@Nonnull BlockState state,
		@Nonnull World worldIn,
		@Nonnull BlockPos pos,
		@Nonnull BlockState newState,
		boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.SPAWN_BLOCKING )
			.ifPresent( capability -> capability.removeSpawnBlocker( spawnBlockFactory.build( pos ) ) );
		super.onReplaced( state, worldIn, pos, newState, isMoving );
	}
}
