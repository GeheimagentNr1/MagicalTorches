package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.elements.creative_mod_tabs.ModCreativeTabs;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;


@Mod.EventBusSubscriber( modid = MagicalTorches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@SubscribeEvent
	public static void handleModConfigLoadingEvent( ModConfigEvent.Loading event ) {
		
		ServerConfig.analyseAndPrintConfig();
	}
	
	@SubscribeEvent
	public static void handleModConfigReloadingEvent( ModConfigEvent.Reloading event ) {
		
		ServerConfig.analyseAndPrintConfig();
	}
	
	@SubscribeEvent
	public static void handleClientSetupEvent( FMLClientSetupEvent event ) {
		
		ModBlocks.BLOCKS.forEach( registryEntry -> {
			Block block = registryEntry.getValue();
			if( block instanceof BlockRenderTypeInterface blockRenderType ) {
				ItemBlockRenderTypes.setRenderLayer( block, blockRenderType.getRenderType() );
			}
		} );
	}
	
	@SubscribeEvent
	public static void handleRegisterCapabilitiesEvent( RegisterCapabilitiesEvent event ) {
		
		event.register( ChickenEggSpawningCapability.class );
		event.register( SpawnBlockingCapability.class );
		event.register( SoundMufflingCapability.class );
	}
	
	@SubscribeEvent
	public static void handleBlocksRegistryEvent( RegisterEvent event ) {
		
		if( event.getRegistryKey().equals( ForgeRegistries.Keys.BLOCKS ) ) {
			event.register(
				ForgeRegistries.Keys.BLOCKS,
				registerHelper -> ModBlocks.BLOCKS.forEach( registryEntry -> registerHelper.register(
					registryEntry.getRegistryName(),
					registryEntry.getValue()
				) )
			);
		}
	}
	
	@SubscribeEvent
	public static void handleItemsRegistryEvent( RegisterEvent event ) {
		
		if( event.getRegistryKey().equals( ForgeRegistries.Keys.ITEMS ) ) {
			Item.Properties properties = new Item.Properties();
			event.register(
				ForgeRegistries.Keys.ITEMS,
				registerHelper -> ModBlocks.BLOCKS.forEach( registryEntry -> {
					if( registryEntry.getValue() instanceof BlockItemInterface blockItem ) {
						registerHelper.register(
							registryEntry.getRegistryName(),
							blockItem.getBlockItem( properties )
						);
					}
				} )
			);
		}
	}
	
	@SubscribeEvent
	public static void handleCreativeModeTabRegisterEvent( CreativeModeTabEvent.Register event ) {
		
		ModCreativeTabs.CREATIVE_TAB_FACTORIES.forEach( creativeModeTabFactory ->
			event.registerCreativeModeTab( creativeModeTabFactory.getName(), creativeModeTabFactory ) );
	}
}
