package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;


public class SpawnBlockingHandler {
	
	
	@NotNull
	private static final String BLOCK_SPAWNING_TAG = ResourceLocation.fromNamespaceAndPath( MagicalTorches.MODID, "block_spawning" )
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
	public void handleFinalizeSpawnEvent( @NotNull FinalizeSpawnEvent event ) {
		
		if( CHECK_SPAWN_NON_BLOCKED_TYPES.contains( event.getSpawnType() ) ) {
			return;
		}
		Entity entity = event.getEntity();
		Level level = entity.getCommandSenderWorld();
		if( level.hasData( ModAttachments.SPAWN_BLOCKING ) ) {
			var capability = level.getData( ModAttachments.SPAWN_BLOCKING );
			if( capability.shouldBlockEntitySpawn( entity ) ) {
				entity.addTag( BLOCK_SPAWNING_TAG );
			}
		}
	}
	
	@SubscribeEvent
	public void handleEntityJoinLevelEvent( @NotNull EntityJoinLevelEvent event ) {
		
		Entity entity = event.getEntity();
		if( entity instanceof Player ) {
			return;
		}
		if( entity.getTags().contains( BLOCK_SPAWNING_TAG ) ) {
			event.setCanceled( true );
			return;
		}
		Level level = event.getLevel();
		
		if( level.hasData( ModAttachments.CHICKEN_EGG_SPAWNING ) ) {
			var capability = level.getData( ModAttachments.CHICKEN_EGG_SPAWNING );
			if( capability.shouldBlockChickenEggSpawn( entity ) ) {
				event.setCanceled( true );
			}
		}
	}
}
