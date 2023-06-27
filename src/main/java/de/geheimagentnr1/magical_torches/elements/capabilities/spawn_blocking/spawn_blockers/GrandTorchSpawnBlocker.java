package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.GrandTorch;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class GrandTorchSpawnBlocker extends HostileMobSpawnBlocker {
	
	
	@NotNull
	public static final ResourceLocation registry_name = ResourceLocationBuilder.build( GrandTorch.registry_name );
	
	public GrandTorchSpawnBlocker( @NotNull BlockPos _pos ) {
		
		super( _pos );
	}
	
	@NotNull
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ServerConfig.getINSTANCE().getGrandTorchRange();
	}
}
