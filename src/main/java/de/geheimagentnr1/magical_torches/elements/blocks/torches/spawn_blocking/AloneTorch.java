package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.AloneTorchSpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;


public class AloneTorch extends SpawnBlockingTorch implements BlockRenderTypeInterface {
	
	
	public static final String registry_name = "alone_torch";
	
	public AloneTorch() {
		
		super(
			AbstractBlock.Properties.create( Material.GLASS ).sound( SoundType.GLASS ),
			registry_name,
			AloneTorchSpawnBlocker.registry_name,
			AloneTorchSpawnBlocker::new
		);
	}
	
	@Nonnull
	@Override
	public VoxelShape getCollisionShape(
		@Nonnull BlockState state, @Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos, @Nonnull ISelectionContext context ) {
		
		return VoxelShapes.fullCube();
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.getTranslucent();
	}
	
	@Override
	protected TextComponent getInformation() {
		
		return new TranslationTextComponent(
			TranslationKeyHelper.buildTooltipTranslationKey( "spawn_blocking_all" ),
			ServerConfig.getAloneTorchRange()
		);
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.ALONE_TORCH, properties, registry_name );
	}
}
