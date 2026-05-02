package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.handlers.ClientSoundMufflingHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;


@EventBusSubscriber( modid = MagicalTorches.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD )
public class MagicalTorchesClientSetup {

	@SubscribeEvent
	public static void onFMLClientSetup( FMLClientSetupEvent event ) {

		NeoForge.EVENT_BUS.register( new ClientSoundMufflingHandler() );
	}
}
