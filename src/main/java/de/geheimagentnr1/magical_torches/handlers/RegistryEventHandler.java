package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapabilityStorage;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.MOD )
public class RegistryEventHandler {
	
	
	@SubscribeEvent
	public static void setup( FMLCommonSetupEvent event ) {
		
		CapabilityManager.INSTANCE.register( SpawnBlockingCapability.class, new SpawnBlockingCapabilityStorage(),
			SpawnBlockingCapability::new );
	}
	
	@SubscribeEvent
	public static void onBlocksRegistry( RegistryEvent.Register<Block> blockRegistryEvent ) {
		
		blockRegistryEvent.getRegistry().registerAll( ModBlocks.BLOCKS );
	}
	
	@SubscribeEvent
	public static void onItemsRegistry( RegistryEvent.Register<Item> itemRegistryEvent ) {
		
		Item.Properties properties = new Item.Properties().group( MagicalTorches.setup.magicalTorchesItemGroup );
		
		for( Block block : ModBlocks.BLOCKS ) {
			if( block instanceof BlockItemInterface ) {
				BlockItemInterface blockItem = (BlockItemInterface)block;
				itemRegistryEvent.getRegistry().register( blockItem.getBlockItem( properties ) );
			}
		}
	}
}
