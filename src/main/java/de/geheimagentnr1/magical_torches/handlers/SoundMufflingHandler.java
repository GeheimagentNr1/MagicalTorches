package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import de.geheimagentnr1.magical_torches.network.InitSoundMufflersMsg;
import de.geheimagentnr1.minecraft_forge_api.events.ForgeEventHandlerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;


public class SoundMufflingHandler implements ForgeEventHandlerInterface {
	
	
	@SubscribeEvent
	@Override
	public void handlePlayerLoggedInEvent( @NotNull PlayerEvent.PlayerLoggedInEvent event ) {
		
		Player player = event.getEntity();
		if( player instanceof ServerPlayer serverPlayer ) {
			InitSoundMufflersMsg.sendToPlayer( serverPlayer );
		}
	}
	
	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	@Override
	public void handlePlaySoundEvent( @NotNull PlaySoundEvent event ) {
		
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
	@Override
	public void handleClientPlayerNetworkLoggingOutEvent( @NotNull ClientPlayerNetworkEvent.LoggingOut event ) {
		
		SoundMufflersHolder.clear();
	}
}
