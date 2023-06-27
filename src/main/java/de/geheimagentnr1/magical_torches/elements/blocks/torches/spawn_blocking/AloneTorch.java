package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers.AloneTorchSpawnBlocker;
import de.geheimagentnr1.minecraft_forge_api.util.TranslationKeyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class AloneTorch extends SpawnBlockingTorch {
	
	
	@NotNull
	public static final String registry_name = "alone_torch";
	
	public AloneTorch() {
		
		super(
			Properties.of()
				.mapColor( MapColor.WOOD )
				.strength( 3.5F )
				.requiresCorrectToolForDrops()
				.sound( SoundType.GLASS ),
			AloneTorchSpawnBlocker.registry_name,
			AloneTorchSpawnBlocker::new
		);
	}
	
	@NotNull
	@Override
	public VoxelShape getCollisionShape(
		@NotNull BlockState state,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull CollisionContext context ) {
		
		return Shapes.block();
	}
	
	@NotNull
	@Override
	protected MutableComponent getInformation() {
		
		return Component.translatable(
			TranslationKeyHelper.generateTooltipTranslationKey( MagicalTorches.MODID, "spawn_blocking_all" ),
			ServerConfig.getINSTANCE().getAloneTorchRange()
		);
	}
}
