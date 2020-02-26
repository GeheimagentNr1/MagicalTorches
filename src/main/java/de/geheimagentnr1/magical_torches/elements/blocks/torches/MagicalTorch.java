package de.geheimagentnr1.magical_torches.elements.blocks.torches;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


//package-private
@SuppressWarnings( "AbstractClassExtendsConcreteClass" )
abstract class MagicalTorch extends Block implements BlockItemInterface {
	
	
	private final ISpawnBlockFactory spawnBlockFactory;
	
	//package-private
	MagicalTorch( Block.Properties properties, String registry_name, ResourceLocation spawn_block_registry_name,
		ISpawnBlockFactory _spawnBlockFactory ) {
		
		super( properties.doesNotBlockMovement().lightValue( 15 ) );
		setRegistryName( registry_name );
		spawnBlockFactory = _spawnBlockFactory;
		SpawnBlockingCapability.registerSpawnBlocker( spawn_block_registry_name, _spawnBlockFactory );
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape( @Nonnull BlockState state, @Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos, ISelectionContext context ) {
		
		return VoxelShapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onBlockAdded( BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent(
			capability -> capability.addSpawnBlocker( spawnBlockFactory.buildSpawnBlocker( pos ) ) );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onReplaced( BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos,
		@Nonnull BlockState newState, boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent(
			capability -> capability.removeSpawnBlocker( spawnBlockFactory.buildSpawnBlocker( pos ) ) );
		super.onReplaced( state, worldIn, pos, newState, isMoving );
	}
}
