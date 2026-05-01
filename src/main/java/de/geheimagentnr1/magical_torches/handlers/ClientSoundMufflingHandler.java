package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.RadiusHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.sound.PlaySoundEvent;
import org.jetbrains.annotations.NotNull;


public class ClientSoundMufflingHandler {


	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public void handlePlaySoundEvent( @NotNull PlaySoundEvent event ) {

		SoundInstance sound = event.getOriginalSound();
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
					}
				}
			} );
		}
	}

	@OnlyIn( Dist.CLIENT )
	@SubscribeEvent
	public void handleClientPlayerNetworkLoggingOutEvent( @NotNull ClientPlayerNetworkEvent.LoggingOut event ) {

		SoundMufflersHolder.clear();
	}
}
