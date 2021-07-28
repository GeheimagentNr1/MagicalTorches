package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.network.InitSoundMufflersMsg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
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


@Mod.EventBusSubscriber( modid = MagicalTorches.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	@SubscribeEvent
	public static void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		Player player = event.getPlayer();
		if( player instanceof ServerPlayer serverPlayer ) {
			InitSoundMufflersMsg.sendToPlayer( serverPlayer );
		}
	}
	
	@SubscribeEvent
	public static void handleWorldAttachCapabilityEvent( AttachCapabilitiesEvent<Level> event ) {
		
		event.addCapability( ChickenEggSpawningCapability.registry_name, new ChickenEggSpawningCapability() );
		event.addCapability( SpawnBlockingCapability.registry_name, new SpawnBlockingCapability() );
		event.addCapability( SoundMufflingCapability.registry_name, new SoundMufflingCapability() );
	}
	
	private static void blockSpawning( Level world, EntityEvent event, Entity entity ) {
		
		world.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent( capability -> {
			if( capability.shouldBlockEntitySpawn( entity ) ) {
				event.setResult( Event.Result.DENY );
			}
		} );
	}
	
	@SubscribeEvent
	public static void handleCheckSpawn( LivingSpawnEvent.CheckSpawn event ) {
		
		if( event.getResult() == Event.Result.ALLOW || event.isSpawner() ) {
			return;
		}
		Entity entity = event.getEntity();
		if( entity instanceof Player ) {
			return;
		}
		blockSpawning( entity.getCommandSenderWorld(), event, entity );
	}
	
	@SubscribeEvent
	public static void handleEntityJoinWorldEvent( EntityJoinWorldEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		Entity entity = event.getEntity();
		if( entity instanceof Player ) {
			return;
		}
		Level level = event.getWorld();
		
		level.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING ).ifPresent( capability -> {
			if( capability.shouldBlockChickenEggSpawn( entity ) ) {
				event.setCanceled( true );
			}
		} );
		if( !event.isCanceled() ) {
			blockSpawning( level, event, entity );
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handlePlaySoundEvent( PlaySoundEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		SoundInstance sound = event.getSound();
		Level level = Minecraft.getInstance().level;
		
		if( level != null ) {
			BlockPos sound_pos = new BlockPos( sound.getX(), sound.getY(), sound.getZ() );
			SoundMufflersHolder.getDimensionSoundMufflers( level.dimension() )
				.ifPresent(
					soundMufflers -> {
						for( SoundMuffler soundMuffler : soundMufflers ) {
							if( soundMuffler.shouldMuffleSound( sound ) && RadiusHelper.isEventInRadiusOfBlock(
								sound_pos,
								soundMuffler.getPos(),
								soundMuffler.getRange()
							) ) {
								event.setResultSound( null );
								event.setResult( Event.Result.DENY );
							}
						}
					}
				);
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleLogoutEvent( ClientPlayerNetworkEvent.LoggedOutEvent event ) {
		
		SoundMufflersHolder.clear();
	}
}
