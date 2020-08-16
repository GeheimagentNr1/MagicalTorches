package de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking;

import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.ISpawnBlockFactory;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;


public abstract class HostileSpawnBlockingTorch extends WoodenSpawnBlockingTorch {
	
	
	//package-private
	HostileSpawnBlockingTorch( String registry_name, ResourceLocation spawn_block_registry_name,
		ISpawnBlockFactory _spawnBlockFactory ) {
		
		super( registry_name, spawn_block_registry_name, _spawnBlockFactory );
	}
	
	@Override
	protected TextComponent getInformation() {
		
		return new TranslationTextComponent( TranslationKeyHelper.buildTooltipTranslationKey(
			"spawn_blocking_hostile" ), spawnBlockFactory.buildSpawnBlocker( BlockPos.ZERO ).getRange() );
	}
}
