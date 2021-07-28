package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.SmallTorchSpawnBlocker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;


public class SmallTorch extends HostileSpawnBlockingTorch {
	
	
	public static final String registry_name = "small_torch";
	
	private static final VoxelShape SHAPE = Block.box( 7, 0, 7, 9, 10, 9 );
	
	public SmallTorch() {
		
		super( registry_name, SmallTorchSpawnBlocker.registry_name, SmallTorchSpawnBlocker::new );
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
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.SMALL_TORCH, properties, registry_name );
	}
}
