package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.minecraft_forge_api.network.AbstractNetwork;
import org.jetbrains.annotations.NotNull;


public class Network extends AbstractNetwork {
	
	
	@NotNull
	private static final Network INSTANCE = new Network();
	
	@NotNull
	public static Network getInstance() {
		
		return INSTANCE;
	}
	
	@NotNull
	@Override
	protected String getModId() {
		
		return MagicalTorches.MODID;
	}
	
	@NotNull
	@Override
	protected String getNetworkName() {
		
		return "main";
	}
	
	@Override
	public void registerPackets() {
		
		getChannel().registerMessage(
			0,
			InitSoundMufflersMsg.class,
			InitSoundMufflersMsg::encode,
			InitSoundMufflersMsg::decode,
			InitSoundMufflersMsg::handle
		);
		getChannel().registerMessage(
			1,
			AddSoundMufflerMsg.class,
			AddSoundMufflerMsg::encode,
			AddSoundMufflerMsg::decode,
			AddSoundMufflerMsg::handle
		);
		getChannel().registerMessage(
			2,
			RemoveSoundMufflerMsg.class,
			RemoveSoundMufflerMsg::encode,
			RemoveSoundMufflerMsg::decode,
			RemoveSoundMufflerMsg::handle
		);
	}
}
