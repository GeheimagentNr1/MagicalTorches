package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.elements.item_groups.ModItemGroups;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


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
		
		for( Block block : ModBlocks.BLOCKS ) {
			if( block instanceof BlockRenderTypeInterface blockRenderType ) {
				ItemBlockRenderTypes.setRenderLayer( block, blockRenderType.getRenderType() );
			}
		}
	}
	
	@SubscribeEvent
	public static void handleCommonSetupEvent( FMLCommonSetupEvent event ) {
		
		CapabilityManager.INSTANCE.register( ChickenEggSpawningCapability.class );
		CapabilityManager.INSTANCE.register( SpawnBlockingCapability.class );
		CapabilityManager.INSTANCE.register( SoundMufflingCapability.class );
	}
	
	@SubscribeEvent
	public static void handleBlocksRegistryEvent( RegistryEvent.Register<Block> blockRegistryEvent ) {
		
		blockRegistryEvent.getRegistry().registerAll( ModBlocks.BLOCKS );
	}
	
	@SubscribeEvent
	public static void handleItemsRegistryEvent( RegistryEvent.Register<Item> itemRegistryEvent ) {
		
		Item.Properties properties = new Item.Properties().tab( ModItemGroups.MAGICAL_TORCHES_ITEM_GROUP );
		
		for( Block block : ModBlocks.BLOCKS ) {
			if( block instanceof BlockItemInterface blockItem ) {
				itemRegistryEvent.getRegistry().register( blockItem.getBlockItem( properties ) );
			}
		}
	}
}
