package de.geheimagentnr1.magical_torches.handlers;

import de.geheimagentnr1.magical_torches.network.InitSoundMufflersMsg;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;


public class CommonSoundMufflingHandler {


	@SubscribeEvent
	public void handlePlayerLoggedInEvent( @NotNull PlayerEvent.PlayerLoggedInEvent event ) {

		Player player = event.getEntity();
		if( player instanceof ServerPlayer serverPlayer ) {
			InitSoundMufflersMsg.sendToPlayer( serverPlayer );
		}
	}
}
