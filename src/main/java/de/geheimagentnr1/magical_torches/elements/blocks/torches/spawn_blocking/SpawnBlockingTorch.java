package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


//package-private
abstract class SpawnBlockingTorch extends BlockWithTooltip {
	
	
	//package-private
	@NotNull
	final ISpawnBlockerFactory spawnBlockFactory;
	
	//package-private
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	SpawnBlockingTorch(
		@NotNull Properties properties,
		@NotNull ResourceLocation spawn_block_registry_name,
		@NotNull ISpawnBlockerFactory _spawnBlockFactory ) {
		
		//noOcclusion instead of noCollission (renamed to noCollision in 1.21.9): the collision flag is only read by the
		//default getCollisionShape, which this block overrides with an empty shape
		super(
			properties.noOcclusion().pushReaction( PushReaction.DESTROY ).lightLevel( value -> 15 )
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
		
		if( !level.isClientSide() ) {
			var capability = level.getData( ModAttachments.SPAWN_BLOCKING );
			capability.addSpawnBlocker( spawnBlockFactory.build( pos ) );
		}
	}
	
	//Since 1.21.5 onRemove is replaced by affectNeighborsAfterRemoval. It is only called on the server when
	//the block is replaced by another block (with neighbor updates).
	@Override
	protected void affectNeighborsAfterRemoval(
		@NotNull BlockState state,
		@NotNull ServerLevel level,
		@NotNull BlockPos pos,
		boolean movedByPiston ) {
		
		var capability = level.getData( ModAttachments.SPAWN_BLOCKING );
		capability.removeSpawnBlocker( spawnBlockFactory.build( pos ) );
		super.affectNeighborsAfterRemoval( state, level, pos, movedByPiston );
	}
}
