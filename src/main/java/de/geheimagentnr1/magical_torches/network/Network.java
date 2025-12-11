package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.NotNull;


public class Network {
	
	
	@NotNull
	public static final ResourceLocation INIT_SOUND_MUFFLERS_ID = ResourceLocation.fromNamespaceAndPath(
		MagicalTorches.MODID,
		"init_sound_mufflers"
	);
	
	@NotNull
	public static final ResourceLocation ADD_SOUND_MUFFLER_ID = ResourceLocation.fromNamespaceAndPath(
		MagicalTorches.MODID,
		"add_sound_muffler"
	);
	
	@NotNull
	public static final ResourceLocation REMOVE_SOUND_MUFFLER_ID = ResourceLocation.fromNamespaceAndPath(
		MagicalTorches.MODID,
		"remove_sound_muffler"
	);
	
	public static void register() {
		
		// Registration is now done via event in NeoForge
	}
	
	public static void onRegisterPayloadHandlers( @NotNull RegisterPayloadHandlersEvent event ) {
		
		PayloadRegistrar registrar = event.registrar( MagicalTorches.MODID );
		
		registrar.playToClient(
			InitSoundMufflersMsg.TYPE,
			InitSoundMufflersMsg.STREAM_CODEC,
			InitSoundMufflersMsg::handle
		);
		
		registrar.playToClient(
			AddSoundMufflerMsg.TYPE,
			AddSoundMufflerMsg.STREAM_CODEC,
			AddSoundMufflerMsg::handle
		);
		
		registrar.playToClient(
			RemoveSoundMufflerMsg.TYPE,
			RemoveSoundMufflerMsg.STREAM_CODEC,
			RemoveSoundMufflerMsg::handle
		);
	}
}
