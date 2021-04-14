package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapabilityStorage;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapabilityStorage;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapabilityStorage;
import de.geheimagentnr1.magical_torches.elements.item_groups.ModItemGroups;
import de.geheimagentnr1.magical_torches.network.UpdateConfigMsg;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@Mod.EventBusSubscriber( modid = MagicalTorches.MODID, bus = Mod.EventBusSubscriber.Bus.MOD )
public class ModEventHandler {
	
	
	@SubscribeEvent
	public static void handleModConfigLoadingEvent( ModConfig.Loading event ) {
		
		ServerConfig.analyseAndPrintConfig();
		UpdateConfigMsg.sendToAll();
	}
	
	@SubscribeEvent
	public static void handleModConfigReloadingEvent( ModConfig.Reloading event ) {
		
		ServerConfig.analyseAndPrintConfig();
		UpdateConfigMsg.sendToAll();
	}
	
	@SubscribeEvent
	public static void handleClientSetupEvent( FMLClientSetupEvent event ) {
		
		for( Block block : ModBlocks.BLOCKS ) {
			if( block instanceof BlockRenderTypeInterface ) {
				BlockRenderTypeInterface blockRenderType = (BlockRenderTypeInterface)block;
				RenderTypeLookup.setRenderLayer( block, blockRenderType.getRenderType() );
			}
		}
	}
	
	@SubscribeEvent
	public static void handleCommonSetupEvent( FMLCommonSetupEvent event ) {
		
		CapabilityManager.INSTANCE.register(
			ChickenEggSpawningCapability.class,
			new ChickenEggSpawningCapabilityStorage(),
			ChickenEggSpawningCapability::new
		);
		CapabilityManager.INSTANCE.register(
			SpawnBlockingCapability.class,
			new SpawnBlockingCapabilityStorage(),
			SpawnBlockingCapability::new
		);
		CapabilityManager.INSTANCE.register(
			SoundMufflingCapability.class,
			new SoundMufflingCapabilityStorage(),
			SoundMufflingCapability::new
		);
	}
	
	@SubscribeEvent
	public static void handleBlocksRegistryEvent( RegistryEvent.Register<Block> blockRegistryEvent ) {
		
		blockRegistryEvent.getRegistry().registerAll( ModBlocks.BLOCKS );
	}
	
	@SubscribeEvent
	public static void handleItemsRegistryEvent( RegistryEvent.Register<Item> itemRegistryEvent ) {
		
		Item.Properties properties = new Item.Properties().tab( ModItemGroups.MAGICAL_TORCHES_ITEM_GROUP );
		
		for( Block block : ModBlocks.BLOCKS ) {
			if( block instanceof BlockItemInterface ) {
				BlockItemInterface blockItem = (BlockItemInterface)block;
				itemRegistryEvent.getRegistry().register( blockItem.getBlockItem( properties ) );
			}
		}
	}
}
