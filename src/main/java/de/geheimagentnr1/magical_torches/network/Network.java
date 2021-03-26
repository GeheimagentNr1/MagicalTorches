package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;


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
			UpdateConfigMsg.class,
			UpdateConfigMsg::encode,
			UpdateConfigMsg::decode,
			UpdateConfigMsg::handle
		);
		CHANNEL.registerMessage(
			1,
			InitSoundMufflersMsg.class,
			InitSoundMufflersMsg::encode,
			InitSoundMufflersMsg::decode,
			InitSoundMufflersMsg::handle
		);
		CHANNEL.registerMessage(
			2,
			AddSoundMufflerMsg.class,
			AddSoundMufflerMsg::encode,
			AddSoundMufflerMsg::decode,
			AddSoundMufflerMsg::handle
		);
		CHANNEL.registerMessage(
			3,
			RemoveSoundMufflerMsg.class,
			RemoveSoundMufflerMsg::encode,
			RemoveSoundMufflerMsg::decode,
			RemoveSoundMufflerMsg::handle
		);
	}
}
