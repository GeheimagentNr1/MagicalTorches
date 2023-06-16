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
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;


@Mod.EventBusSubscriber( modid = MagicalTorches.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE )
public class ForgeEventHandler {
	
	
	private static final String BLOCK_SPAWNING_TAG = MagicalTorches.MODID + ":block_spawning";
	
	private static final List<MobSpawnType> CHECK_SPAWN_NON_BLOCKED_TYPES = List.of(
		MobSpawnType.BUCKET,
		MobSpawnType.SPAWN_EGG,
		MobSpawnType.COMMAND,
		MobSpawnType.DISPENSER,
		MobSpawnType.SPAWNER
	);
	
	@SubscribeEvent
	public static void handlePlayerLoggedInEvent( PlayerEvent.PlayerLoggedInEvent event ) {
		
		Player player = event.getEntity();
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
	
	private static void blockSpawning( Level world, Entity entity ) {
		
		world.getCapability( ModCapabilities.SPAWN_BLOCKING ).ifPresent( capability -> {
			if( capability.shouldBlockEntitySpawn( entity ) ) {
				entity.addTag( BLOCK_SPAWNING_TAG );
			}
		} );
	}
	
	@SubscribeEvent
	public static void handleCheckSpawn( MobSpawnEvent.FinalizeSpawn event ) {
		
		if( event.getResult() == Event.Result.ALLOW ||
			CHECK_SPAWN_NON_BLOCKED_TYPES.contains( event.getSpawnType() ) ) {
			return;
		}
		Entity entity = event.getEntity();
		blockSpawning( entity.getCommandSenderWorld(), entity );
	}
	
	@SubscribeEvent
	public static void handleEntityJoinLevelEvent( EntityJoinLevelEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		Entity entity = event.getEntity();
		if( entity instanceof Player ) {
			return;
		}
		if( entity.getTags().contains( BLOCK_SPAWNING_TAG ) ) {
			event.setCanceled( true );
		}
		Level level = event.getLevel();
		
		level.getCapability( ModCapabilities.CHICKEN_EGG_SPAWNING ).ifPresent( capability -> {
			if( capability.shouldBlockChickenEggSpawn( entity ) ) {
				event.setCanceled( true );
			}
		} );
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handlePlaySoundEvent( PlaySoundEvent event ) {
		
		if( event.getResult() == Event.Result.ALLOW ) {
			return;
		}
		SoundInstance sound = event.getSound();
		Level level = Minecraft.getInstance().level;
		
		if( sound != null && level != null ) {
			BlockPos sound_pos = BlockPos.containing( sound.getX(), sound.getY(), sound.getZ() );
			SoundMufflersHolder.getDimensionSoundMufflers( level.dimension() ).ifPresent( soundMufflers -> {
				for( SoundMuffler soundMuffler : soundMufflers ) {
					if( soundMuffler.shouldMuffleSound( sound ) && RadiusHelper.isEventInRadiusOfBlock(
						sound_pos,
						soundMuffler.getPos(),
						soundMuffler.getRange()
					) ) {
						event.setSound( null );
						event.setResult( Event.Result.DENY );
					}
				}
			} );
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public static void handleLoggingOutEvent( ClientPlayerNetworkEvent.LoggingOut event ) {
		
		SoundMufflersHolder.clear();
	}
}
