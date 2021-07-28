package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fmllegacy.network.NetworkRegistry;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;


public class Network {
	
	
	private static final String PROTOCOL_VERSION = "1";
	
	//package-private
	static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
		new ResourceLocation( MagicalTorches.MODID, "main" ),
		() -> PROTOCOL_VERSION,
		PROTOCOL_VERSION::equals,
		PROTOCOL_VERSION::equals
	);
	
	public static void init() {
		
		CHANNEL.registerMessage(
			0,
			InitSoundMufflersMsg.class,
			InitSoundMufflersMsg::encode,
			InitSoundMufflersMsg::decode,
			InitSoundMufflersMsg::handle
		);
		CHANNEL.registerMessage(
			1,
			AddSoundMufflerMsg.class,
			AddSoundMufflerMsg::encode,
			AddSoundMufflerMsg::decode,
			AddSoundMufflerMsg::handle
		);
		CHANNEL.registerMessage(
			2,
			RemoveSoundMufflerMsg.class,
			RemoveSoundMufflerMsg::encode,
			RemoveSoundMufflerMsg::decode,
			RemoveSoundMufflerMsg::handle
		);
	}
}
