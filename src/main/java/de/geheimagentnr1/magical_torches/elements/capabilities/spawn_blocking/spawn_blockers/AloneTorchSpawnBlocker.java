package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.MainConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.AloneTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class AloneTorchSpawnBlocker extends SpawnBlocker {
	
	
	public static final ResourceLocation registry_name = ResourceLocationBuilder.build( AloneTorch.registry_name );
	
	public AloneTorchSpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return MainConfig.getAloneTorchRange();
	}
	
	@Override
	public boolean shouldBlockEntity( Entity entity ) {
		
		return true;
	}
}
