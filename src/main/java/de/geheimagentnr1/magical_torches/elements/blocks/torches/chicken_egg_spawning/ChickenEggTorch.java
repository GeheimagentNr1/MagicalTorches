package de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.chicken_egg_blockers.ChickenEggTorchBlocker;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;


public class ChickenEggTorch extends BlockWithTooltip implements BlockItemInterface, BlockRenderTypeInterface {
	
	
	public static final String registry_name = "chicken_egg_torch";
	
	private static final VoxelShape SHAPE = Block.box( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	private final ISpawnBlockerFactory spawnBlockFactory;
	
	public ChickenEggTorch() {
		
		super( Properties.of( Material.WOOD ).noCollission().strength( 3 ).sound( SoundType.WOOD ) );
		spawnBlockFactory = ChickenEggTorchBlocker::new;
		ChickenEggSpawningCapability.registerChickenEggBlocker(
			ChickenEggTorchBlocker.registry_name,
			spawnBlockFactory
		);
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.cutout();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape(
		@Nonnull BlockState state,
		@Nonnull BlockGetter level,
		@Nonnull BlockPos pos,
		@Nonnull CollisionContext context ) {
		
		return SHAPE;
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
	
	@Override
	public MutableComponent getInformation() {
		
		if( ServerConfig.getShouldInvertChickenEggBlocking() ) {
			return Component.translatable(
				TranslationKeyHelper.buildTooltipTranslationKey( "chicken_egg_spawning_enable" ),
				ServerConfig.getChickenEggTorchRange()
			);
		} else {
			return Component.translatable(
				TranslationKeyHelper.buildTooltipTranslationKey( "chicken_egg_spawning_blocking" ),
				ServerConfig.getChickenEggTorchRange()
			);
		}
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onPlace(
		@Nonnull BlockState state,
		@Nonnull Level level,
		@Nonnull BlockPos pos,
		@Nonnull BlockState oldState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING ).ifPresent(
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
		
		level.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING )
			.ifPresent( capability -> capability.removeSpawnBlocker( spawnBlockFactory.build( pos ) ) );
		super.onRemove( state, level, pos, newState, isMoving );
	}
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.CHICKEN_EGG_TORCH, properties );
	}
}
