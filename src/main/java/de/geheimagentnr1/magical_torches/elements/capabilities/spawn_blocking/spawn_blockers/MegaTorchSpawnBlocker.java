package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ModConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.MegaTorch;
import de.geheimagentnr1.magical_torches.helper.ResourceLocationBuilder;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class MegaTorchSpawnBlocker extends MobSpawnBlocker {
	
	
	public final static ResourceLocation registry_name = ResourceLocationBuilder.build( MegaTorch.registry_name );
	
	public MegaTorchSpawnBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ModConfig.getMegaTorchRange();
	}
}
