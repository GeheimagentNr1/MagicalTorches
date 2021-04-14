package de.geheimagentnr1.magical_torches.network;


import de.geheimagentnr1.magical_torches.config.ClientConfigHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;


public class InitSoundMufflersMsg {
	
	
	private final TreeMap<DimensionType, TreeSet<SoundMuffler>> soundMufflers;
	
	private InitSoundMufflersMsg( TreeMap<DimensionType, TreeSet<SoundMuffler>> _soundMufflers ) {
		
		soundMufflers = _soundMufflers;
	}
	
	//package-private
	static InitSoundMufflersMsg decode( PacketBuffer buffer ) {
		
		TreeMap<DimensionType, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		int dimensionCount = buffer.readInt();
		for( int i = 0; i < dimensionCount; i++ ) {
			DimensionType dimension = DimensionType.byName( buffer.readResourceLocation() );
			TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
			dimensionSoundMufflers.put( dimension, soundMufflers );
			int soundMufflersCount = buffer.readInt();
			for( int j = 0; j < soundMufflersCount; j++ ) {
				soundMufflers.add( SoundMufflingCapability.buildSoundMuffler(
					buffer.readResourceLocation(),
					buffer.readBlockPos()
				) );
			}
		}
		return new InitSoundMufflersMsg( dimensionSoundMufflers );
	}
	
	//package-private
	void encode( PacketBuffer buffer ) {
		
		buffer.writeInt( soundMufflers.size() );
		soundMufflers.forEach(
			( dimension, soundMufflersSet ) -> {
				buffer.writeResourceLocation( Objects.requireNonNull( dimension.getRegistryName() ) );
				buffer.writeInt( soundMufflersSet.size() );
				soundMufflersSet.forEach( soundMuffler -> {
					buffer.writeResourceLocation( soundMuffler.getRegistryName() );
					buffer.writeBlockPos( soundMuffler.getPos() );
				} );
			}
		);
	}
	
	public static void sendToPlayer( ServerPlayerEntity playerEntity ) {
		
		TreeMap<DimensionType, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		Objects.requireNonNull( playerEntity.getServer() ).getWorlds()
			.forEach(
				serverWorld -> {
					TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
					dimensionSoundMufflers.put( serverWorld.getDimension().getType(), soundMufflers );
					serverWorld.getCapability( ModCapabilities.SOUND_MUFFLING )
						.ifPresent( soundMufflingCapability ->
							soundMufflers.addAll( soundMufflingCapability.getSoundMufflers() )
						);
				}
			);
		Network.CHANNEL.send(
			PacketDistributor.PLAYER.with( () -> playerEntity ),
			new InitSoundMufflersMsg( dimensionSoundMufflers )
		);
	}
	
	//package-private
	static void handle( InitSoundMufflersMsg initSoundMufflersMsg, Supplier<NetworkEvent.Context> contextSupplier ) {
		
		ClientConfigHolder.setDimensionSoundMufflers( initSoundMufflersMsg.soundMufflers );
		contextSupplier.get().setPacketHandled( true );
	}
}
