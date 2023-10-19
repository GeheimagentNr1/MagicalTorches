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
		
		getChannel().messageBuilder( InitSoundMufflersMsg.class )
			.encoder( InitSoundMufflersMsg::encode )
			.decoder( InitSoundMufflersMsg::decode )
			.consumerNetworkThread( InitSoundMufflersMsg::handle )
			.add();
		getChannel().messageBuilder( AddSoundMufflerMsg.class )
			.encoder( AddSoundMufflerMsg::encode )
			.decoder( AddSoundMufflerMsg::decode )
			.consumerNetworkThread( AddSoundMufflerMsg::handle )
			.add();
		getChannel().messageBuilder( RemoveSoundMufflerMsg.class )
			.encoder( RemoveSoundMufflerMsg::encode )
			.decoder( RemoveSoundMufflerMsg::decode )
			.consumerNetworkThread( RemoveSoundMufflerMsg::handle )
			.add();
	}
}
