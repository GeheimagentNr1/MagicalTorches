package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.config.ClientConfigHolder;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class UpdateConfigMsg {
	
	
	private final int soundMufflingTorchRange;
	
	private final List<SoundCategory> soundMufflingTorchToMuffleSounds;
	
	private UpdateConfigMsg( int _soundMufflingTorchRange, List<SoundCategory> _soundMufflingTorchToMuffleSounds ) {
		
		soundMufflingTorchRange = _soundMufflingTorchRange;
		soundMufflingTorchToMuffleSounds = _soundMufflingTorchToMuffleSounds;
	}
	
	//package-private
	static UpdateConfigMsg decode( PacketBuffer buffer ) {
		
		int soundMufflingTorchRange = buffer.readInt();
		int size = buffer.readInt();
		List<SoundCategory> soundCategories = new ArrayList<>();
		for( int i = 0; i < size; i++ ) {
			soundCategories.add( SoundCategory.values()[buffer.readInt()] );
		}
		return new UpdateConfigMsg( soundMufflingTorchRange, soundCategories );
	}
	
	//package-private
	void encode( PacketBuffer buffer ) {
		
		buffer.writeInt( soundMufflingTorchRange );
		buffer.writeInt( soundMufflingTorchToMuffleSounds.size() );
		for( SoundCategory soundCategory : soundMufflingTorchToMuffleSounds ) {
			buffer.writeInt( soundCategory.ordinal() );
		}
	}
	
	public static void sendToPlayer( ServerPlayerEntity playerEntity ) {
		
		Network.CHANNEL.send(
			PacketDistributor.PLAYER.with( () -> playerEntity ),
			new UpdateConfigMsg(
				ServerConfig.getSoundMufflingTorchRange(),
				ServerConfig.getSoundMufflingTorchToMuffleSounds()
			)
		);
	}
	
	public static void sendToAll() {
		
		Network.CHANNEL.send(
			PacketDistributor.ALL.noArg(),
			new UpdateConfigMsg(
				ServerConfig.getSoundMufflingTorchRange(),
				ServerConfig.getSoundMufflingTorchToMuffleSounds()
			)
		);
	}
	
	//package-private
	static void handle( UpdateConfigMsg updateConfigMsg, Supplier<NetworkEvent.Context> contextSupplier ) {
		
		ClientConfigHolder.setSoundMufflingTorchRange( updateConfigMsg.soundMufflingTorchRange );
		ClientConfigHolder.setSoundMufflingTorchToMuffleSounds( updateConfigMsg.soundMufflingTorchToMuffleSounds );
		contextSupplier.get().setPacketHandled( true );
	}
}
