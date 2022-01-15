package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.AloneTorchSpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;


public class AloneTorch extends SpawnBlockingTorch implements BlockRenderTypeInterface {
	
	
	public static final String registry_name = "alone_torch";
	
	public AloneTorch() {
		
		super(
			Properties.of( Material.GLASS ).strength( 3.5F ).requiresCorrectToolForDrops().sound( SoundType.GLASS ),
			registry_name,
			AloneTorchSpawnBlocker.registry_name,
			AloneTorchSpawnBlocker::new
		);
	}
	
	@Nonnull
	@Override
	public VoxelShape getCollisionShape(
		@Nonnull BlockState state,
		@Nonnull BlockGetter level,
		@Nonnull BlockPos pos,
		@Nonnull CollisionContext context ) {
		
		return Shapes.block();
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.translucent();
	}
	
	@Override
	protected MutableComponent getInformation() {
		
		return new TranslatableComponent(
			TranslationKeyHelper.buildTooltipTranslationKey( "spawn_blocking_all" ),
			ServerConfig.getAloneTorchRange()
		);
	}
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.ALONE_TORCH, properties, registry_name );
	}
}
