package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.SmallTorchSpawnBlocker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class SmallTorch extends HostileSpawnBlockingTorch {
	
	
	@NotNull
	public static final String registry_name = "small_torch";
	
	@NotNull
	private static final VoxelShape SHAPE = Block.box( 7, 0, 7, 9, 10, 9 );
	
	public SmallTorch() {
		
		super(
			SmallTorchSpawnBlocker.registry_name,
			SmallTorchSpawnBlocker::new
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
}
