package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.events.ForgeEventHandlerInterface;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SpawnBlockingHandler implements ForgeEventHandlerInterface {
	
	
	@NotNull
	private static final String BLOCK_SPAWNING_TAG = new ResourceLocation( MagicalTorches.MODID, "block_spawning" )
		.toString();
	
	@NotNull
	private static final List<MobSpawnType> CHECK_SPAWN_NON_BLOCKED_TYPES = List.of(
		MobSpawnType.BUCKET,
		MobSpawnType.SPAWN_EGG,
		MobSpawnType.COMMAND,
		MobSpawnType.DISPENSER,
		MobSpawnType.SPAWNER
	);
	
	@SubscribeEvent
	@Override
	public void handleMobSpawnFinalizeSpawnEvent( @NotNull MobSpawnEvent.FinalizeSpawn event ) {
		
		if( event.getResult() == Event.Result.ALLOW ||
			CHECK_SPAWN_NON_BLOCKED_TYPES.contains( event.getSpawnType() ) ) {
			return;
		}
		Entity entity = event.getEntity();
		entity.getCommandSenderWorld().getCapability( ModCapabilitiesRegisterFactory.SPAWN_BLOCKING ).ifPresent(
			capability -> {
				if( capability.shouldBlockEntitySpawn( entity ) ) {
					entity.addTag( BLOCK_SPAWNING_TAG );
				}
			} );
	}
	
	@SubscribeEvent
	@Override
	public void handleEntityJoinLevelEvent( @NotNull EntityJoinLevelEvent event ) {
		
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
		
		level.getCapability( ModCapabilitiesRegisterFactory.CHICKEN_EGG_SPAWNING ).ifPresent( capability -> {
			if( capability.shouldBlockChickenEggSpawn( entity ) ) {
				event.setCanceled( true );
			}
		} );
	}
}
