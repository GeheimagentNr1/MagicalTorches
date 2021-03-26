package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.config.ClientConfigHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.network.InitSoundMufflersMsg;
import de.geheimagentnr1.magical_torches.network.UpdateConfigMsg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@SuppressWarnings( "unused" )
@Mod.EventBusSubscriber( bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void onPlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		PlayerEntity playerEntity = event.getPlayer();
		if( playerEntity instanceof ServerPlayerEntity ) {
			ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)playerEntity;
			UpdateConfigMsg.sendToPlayer( serverPlayerEntity );
			InitSoundMufflersMsg.sendToPlayer( serverPlayerEntity );
		}
	}
	
	@SubscribeEvent
	public static void onWorldAttachCapabilityEvent( AttachCapabilitiesEvent<World> event ) {
		
		event.addCapability( ChickenEggSpawningCapability.registry_name, new ChickenEggSpawningCapability() );
		event.addCapability( SpawnBlockingCapability.registry_name, new SpawnBlockingCapability() );
		event.addCapability( SoundMufflingCapability.registry_name, new SoundMufflingCapability() );
	}
	
	private static void blockSpawning( World world, EntityEvent event, Entity entity ) {
		
		world.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent( capability -> {
			if( capability.shouldBlockEntitySpawn( entity ) ) {
				event.setResult( Event.Result.DENY );
			}
		} );
	}
	
	@SubscribeEvent
	public static void onCheckSpawn( LivingSpawnEvent.CheckSpawn event ) {
		
		if( event.getResult() == Event.Result.ALLOW || event.isSpawner() ) {
			return;
		}
		Entity entity = event.getEntity();
		if( entity instanceof PlayerEntity ) {
			return;
		}
		blockSpawning( entity.getEntityWorld(), event, entity );
	}
	
	@SubscribeEvent
	public static void handleEntityJoinWorldEvent( EntityJoinWorldEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		Entity entity = event.getEntity();
		if( entity instanceof PlayerEntity ) {
			return;
		}
		World world = event.getWorld();
		
		world.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING ).ifPresent( capability -> {
			if( capability.shouldBlockChickenEggSpawn( entity ) ) {
				event.setCanceled( true );
			}
		} );
		if( !event.isCanceled() ) {
			blockSpawning( world, event, entity );
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handlePlaySoundEvent( PlaySoundEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		ISound sound = event.getSound();
		World world = Minecraft.getInstance().world;
		
		if( world != null ) {
			BlockPos sound_pos = new BlockPos( sound.getX(), sound.getY(), sound.getZ() );
			ClientConfigHolder.getDimensionSoundMufflers( world.func_234923_W_() )
				.ifPresent( soundMufflers -> {
					for( SoundMuffler soundMuffler : soundMufflers ) {
						if( soundMuffler.shouldMuffleSound( sound ) && RadiusHelper.isEventInRadiusOfBlock( sound_pos,
							soundMuffler.getPos(), soundMuffler.getRange()
						) ) {
							event.setResultSound( null );
							event.setResult( Event.Result.DENY );
						}
					}
				} );
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleLogoutEvent( ClientPlayerNetworkEvent.LoggedOutEvent event ) {
		
		ClientConfigHolder.clear();
	}
}
