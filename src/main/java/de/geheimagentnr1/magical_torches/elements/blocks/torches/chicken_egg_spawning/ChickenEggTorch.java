package de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.chicken_egg_blockers.ChickenEggTorchBlocker;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class ChickenEggTorch extends BlockWithTooltip {
	
	
	@NotNull
	public static final String registry_name = "chicken_egg_torch";
	
	@NotNull
	private static final VoxelShape SHAPE = Block.box( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	@NotNull
	private final ISpawnBlockerFactory spawnBlockFactory;
	
	public ChickenEggTorch() {
		
		super(
			Properties.of()
				.mapColor( MapColor.WOOD )
				.noCollission()
				.pushReaction( PushReaction.DESTROY )
				.strength( 3 )
				.sound( SoundType.WOOD )
		);
		spawnBlockFactory = ChickenEggTorchBlocker::new;
		ChickenEggSpawningCapability.registerChickenEggBlocker(
			ChickenEggTorchBlocker.registry_name,
			spawnBlockFactory
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@NotNull
	@Override
	public VoxelShape getShape(
		@NotNull BlockState state,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull CollisionContext context ) {
		
		return SHAPE;
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
	
	@NotNull
	@Override
	public MutableComponent getInformation() {
		
		ServerConfig serverConfig = ServerConfig.getINSTANCE();
		if( serverConfig.getShouldInvertChickenEggBlocking() ) {
			return Component.translatable(
				"tooltip." + MagicalTorches.MODID + ".chicken_egg_spawning_enable",
				serverConfig.getChickenEggTorchRange()
			);
		} else {
			return Component.translatable(
				"tooltip." + MagicalTorches.MODID + ".chicken_egg_spawning_blocking",
				serverConfig.getChickenEggTorchRange()
			);
		}
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onPlace(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState oldState,
		boolean isMoving ) {
		
		if( !level.isClientSide ) {
			var capability = level.getData( ModAttachments.CHICKEN_EGG_SPAWNING );
			capability.addSpawnBlocker( spawnBlockFactory.build( pos ) );
		}
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onRemove(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState newState,
		boolean isMoving ) {
		
		if( !level.isClientSide ) {
			var capability = level.getData( ModAttachments.CHICKEN_EGG_SPAWNING );
			capability.removeSpawnBlocker( spawnBlockFactory.build( pos ) );
		}
		super.onRemove( state, level, pos, newState, isMoving );
	}
}
