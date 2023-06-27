package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.minecraft_forge_api.elements.blocks.BlockItemInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


//package-private
abstract class SpawnBlockingTorch extends BlockWithTooltip implements BlockItemInterface {
	
	
	//package-private
	@NotNull
	final ISpawnBlockerFactory spawnBlockFactory;
	
	//package-private
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	SpawnBlockingTorch(
		@NotNull Properties properties,
		@NotNull ResourceLocation spawn_block_registry_name,
		@NotNull ISpawnBlockerFactory _spawnBlockFactory ) {
		
		super(
			properties.noCollission().pushReaction( PushReaction.DESTROY ).lightLevel( value -> 15 )
		);
		spawnBlockFactory = _spawnBlockFactory;
		SpawnBlockingCapability.registerSpawnBlocker( spawn_block_registry_name, _spawnBlockFactory );
	}
	
	@SuppressWarnings( "deprecation" )
	@NotNull
	@Override
	public VoxelShape getCollisionShape(
		@NotNull BlockState state,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull CollisionContext context ) {
		
		return Shapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onPlace(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState oldState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilitiesRegisterFactory.SPAWN_BLOCKING ).ifPresent(
			capability -> capability.addSpawnBlocker( spawnBlockFactory.build( pos ) )
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onRemove(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState newState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilitiesRegisterFactory.SPAWN_BLOCKING ).ifPresent(
			capability -> capability.removeSpawnBlocker( spawnBlockFactory.build( pos ) )
		);
		super.onRemove( state, level, pos, newState, isMoving );
	}
}
