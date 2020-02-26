package de.geheimagentnr1.magical_torches.elements.blocks.torches;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blocks.AloneTorchSpawnBlocker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;


public class AloneTorch extends MagicalTorch {
	
	
	public final static String registry_name = "alone_torch";
	
	public AloneTorch() {
		
		super( Block.Properties.create( Material.GLASS ).hardnessAndResistance( 50.0F, 1200.0F )
				.sound( SoundType.GLASS ), registry_name, AloneTorchSpawnBlocker.registry_name,
			AloneTorchSpawnBlocker::new );
	}
	
	@Nonnull
	@Override
	public VoxelShape getCollisionShape( @Nonnull BlockState state, @Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos, ISelectionContext context ) {
		
		return VoxelShapes.fullCube();
	}
	
	@Nonnull
	@Override
	public BlockRenderLayer getRenderLayer() {
		
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.ALONE_TORCH, properties, registry_name );
	}
}
