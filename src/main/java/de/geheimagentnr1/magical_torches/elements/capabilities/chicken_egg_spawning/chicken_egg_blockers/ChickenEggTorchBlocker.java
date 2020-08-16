package de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.chicken_egg_blockers;

import de.geheimagentnr1.magical_torches.config.MainConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning.ChickenEggTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class ChickenEggTorchBlocker extends SpawnBlocker {
	
	
	public static final ResourceLocation registry_name =
		ResourceLocationBuilder.build( ChickenEggTorch.registry_name );
	
	public ChickenEggTorchBlocker( BlockPos _pos ) {
		
		super( _pos );
	}
	
	@Override
	public ResourceLocation getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return MainConfig.getChickenEggTorchRange();
	}
	
	@Override
	public boolean shouldBlockEntity( Entity entity ) {
		
		return entity instanceof ItemEntity && ( (ItemEntity)entity ).getItem().getItem() == Items.EGG;
	}
}
