package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.SmallTorch;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class SmallTorchSpawnBlocker extends MobSpawnBlocker {
	
	
	public static final ResourceLocation registry_name = ResourceLocationBuilder.build( SmallTorch.registry_name );
	
	public SmallTorchSpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ServerConfig.getSmallTorchRange();
	}
}
