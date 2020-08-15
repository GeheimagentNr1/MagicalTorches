package de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.config.MainConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.chicken_egg_blockers.ChickenEggTorchBlocker;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class ChickenEggTorch extends BlockWithTooltip implements BlockItemInterface {
	
	
	public static final String registry_name = "chicken_egg_torch";
	
	private static final VoxelShape SHAPE = Block.makeCuboidShape( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	private final ISpawnBlockFactory spawnBlockFactory;
	
	public ChickenEggTorch() {
		
		super( Block.Properties.create( Material.WOOD ).doesNotBlockMovement().hardnessAndResistance( 3 )
			.sound( SoundType.WOOD ) );
		setRegistryName( registry_name );
		spawnBlockFactory = ChickenEggTorchBlocker::new;
		ChickenEggSpawningCapability.registerChickenEggBlocker( ChickenEggTorchBlocker.registry_name,
			spawnBlockFactory );
	}
	
	@Nonnull
	@Override
	public BlockRenderLayer getRenderLayer() {
		
		return BlockRenderLayer.CUTOUT;
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
	
	@Override
	public TextComponent getInformation() {
		
		if( MainConfig.getShouldInvertChickenEggBlocking() ) {
			return new TranslationTextComponent( TranslationKeyHelper.buildTooltipTranslationKey(
				"chicken_egg_spawning_enable" ), MainConfig.getChickenEggTorchRange() );
		} else {
			return new TranslationTextComponent( TranslationKeyHelper.buildTooltipTranslationKey(
				"chicken_egg_spawning_blocking" ), MainConfig.getChickenEggTorchRange() );
		}
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
