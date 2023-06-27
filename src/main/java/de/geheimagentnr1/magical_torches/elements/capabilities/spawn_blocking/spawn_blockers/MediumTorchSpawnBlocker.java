package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.MediumTorch;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class MediumTorchSpawnBlocker extends HostileMobSpawnBlocker {
	
	
	@NotNull
	public static final ResourceLocation registry_name = ResourceLocationBuilder.build( MediumTorch.registry_name );
	
	public MediumTorchSpawnBlocker( @NotNull BlockPos _pos ) {
		
		super( _pos );
	}
	
	@NotNull
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ServerConfig.getINSTANCE().getMediumTorchRange();
	}
}
