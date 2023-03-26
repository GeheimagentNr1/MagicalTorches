package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.AloneTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;


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
		
		return ServerConfig.getAloneTorchRange();
	}
	
	@Override
	public boolean shouldBlockEntity( Entity entity ) {
		
		return entity instanceof Mob;
	}
}
