package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;


//package-private
abstract class SpawnBlockingTorch extends BlockWithTooltip implements BlockItemInterface {
	
	
	//package-private
	final ISpawnBlockerFactory spawnBlockFactory;
	
	//package-private
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	SpawnBlockingTorch(
		@Nonnull Properties properties,
		@Nonnull String registry_name,
		@Nonnull ResourceLocation spawn_block_registry_name,
		@Nonnull ISpawnBlockerFactory _spawnBlockFactory ) {
		
		super( properties.noCollission().lightLevel( value -> 15 ) );
		setRegistryName( registry_name );
		spawnBlockFactory = _spawnBlockFactory;
		SpawnBlockingCapability.registerSpawnBlocker( spawn_block_registry_name, _spawnBlockFactory );
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape(
		@Nonnull BlockState state,
		@Nonnull BlockGetter level,
		@Nonnull BlockPos pos,
		@Nonnull CollisionContext context ) {
		
		return Shapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public PushReaction getPistonPushReaction( @Nonnull BlockState state ) {
		
		return PushReaction.DESTROY;
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onPlace(
		@Nonnull BlockState state,
		@Nonnull Level level,
		@Nonnull BlockPos pos,
		@Nonnull BlockState oldState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent(
			capability -> capability.addSpawnBlocker( spawnBlockFactory.build( pos ) )
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onRemove(
		@Nonnull BlockState state,
		@Nonnull Level level,
		@Nonnull BlockPos pos,
		@Nonnull BlockState newState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent(
			capability -> capability.removeSpawnBlocker( spawnBlockFactory.build( pos ) )
		);
		super.onRemove( state, level, pos, newState, isMoving );
	}
}
