package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.GrandTorchSpawnBlocker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class GrandTorch extends HostileSpawnBlockingTorch {
	
	
	@NotNull
	public static final String registry_name = "grand_torch";
	
	@NotNull
	private static final VoxelShape SHAPE = Block.box( 6, 0, 6, 10, 12, 10 );
	
	public GrandTorch() {
		
		super(
			GrandTorchSpawnBlocker.registry_name,
			GrandTorchSpawnBlocker::new
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
