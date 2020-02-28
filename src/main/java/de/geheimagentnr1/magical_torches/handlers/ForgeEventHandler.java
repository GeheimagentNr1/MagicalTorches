package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.config.ModConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.SoundMufflingClientCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handlerServerStartEvent( FMLServerStartingEvent event ) {
		
		ModConfig.load();
	}
	
	@SubscribeEvent
	public static void onWorldAttachCapabilityEvent( AttachCapabilitiesEvent<World> event ) {
		
		event.addCapability( SpawnBlockingCapability.registry_name, new SpawnBlockingCapability() );
	}
	
	@SubscribeEvent
	public static void onCheckSpawn( LivingSpawnEvent.CheckSpawn event ) {
		
		if( event.getResult() == Event.Result.ALLOW || event.isSpawner() ) {
			return;
		}
		Entity entity = event.getEntity();
		World world = entity.getEntityWorld();
		
		world.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent( capability -> {
			if( capability.shouldBlockEntitySpawn( entity ) ) {
				event.setResult( Event.Result.DENY );
			}
		} );
	}
	
	@SubscribeEvent
	public static void handlePlaySoundEvent( PlaySoundEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		ISound sound = event.getSound();
		World world = Minecraft.getInstance().world;
		
		if( world != null ) {
			if( SoundMufflingClientCapability.shouldMuffleSound( sound ) ) {
				event.setResultSound( null );
			}
		}
	}
	
	@SubscribeEvent
	public static void handleLogoutInEvent( ClientPlayerNetworkEvent.LoggedInEvent event ) {
		
		SoundMufflingClientCapability.init();
	}
	
	@SubscribeEvent
	public static void handleLogoutOutEvent( ClientPlayerNetworkEvent.LoggedOutEvent event ) {
		
		SoundMufflingClientCapability.clear();
	}
}
