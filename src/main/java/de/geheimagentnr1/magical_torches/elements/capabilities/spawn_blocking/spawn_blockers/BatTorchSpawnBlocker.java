package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ModConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.BatTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class BatTorchSpawnBlocker extends SpawnBlocker {
	
	
	public final static ResourceLocation registry_name = ResourceLocationBuilder.build( BatTorch.registry_name );
	
	public BatTorchSpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ModConfig.getBatTorchRange();
	}
	
	@Override
	public boolean shouldBlockEntity( Entity entity ) {
		
		return entity instanceof BatEntity;
	}
}
