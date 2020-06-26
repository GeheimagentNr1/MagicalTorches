package de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.chicken_egg_blockers.ChickenEggTorchBlocker;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class ChickenEggTorch extends Block implements BlockItemInterface, BlockRenderTypeInterface {
	
	
	public final static String registry_name = "chicken_egg_torch";
	
	private final static VoxelShape SHAPE = Block.makeCuboidShape( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	private final ISpawnBlockFactory spawnBlockFactory;
	
	public ChickenEggTorch() {
		
		super( AbstractBlock.Properties.create( Material.WOOD ).doesNotBlockMovement().func_235838_a_( value -> 15 )
			.hardnessAndResistance( 3 ).sound( SoundType.WOOD ) );
		setRegistryName( registry_name );
		spawnBlockFactory = ChickenEggTorchBlocker::new;
		ChickenEggSpawningCapability.registerChickenEggBlocker( ChickenEggTorchBlocker.registry_name,
			spawnBlockFactory );
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.getCutout();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape( @Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos,
		@Nonnull ISelectionContext context ) {
		
		return SHAPE;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape( @Nonnull BlockState state, @Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos, @Nonnull ISelectionContext context ) {
		
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
	public void onBlockAdded( @Nonnull BlockState state, World worldIn, @Nonnull BlockPos pos,
		@Nonnull BlockState oldState, boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING ).ifPresent(
			capability -> capability.addSpawnBlocker( spawnBlockFactory.buildSpawnBlocker( pos ) ) );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onReplaced( @Nonnull BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos,
		@Nonnull BlockState newState, boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING ).ifPresent(
			capability -> capability.removeSpawnBlocker( spawnBlockFactory.buildSpawnBlocker( pos ) ) );
		super.onReplaced( state, worldIn, pos, newState, isMoving );
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.CHICKEN_EGG_TORCH, properties, registry_name );
	}
}
