package de.geheimagentnr1.magical_torches.network;


import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;


public class InitSoundMufflersMsg {
	
	
	private final TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> soundMufflers;
	
	private InitSoundMufflersMsg( TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> _soundMufflers ) {
		
		soundMufflers = _soundMufflers;
	}
	
	//package-private
	static InitSoundMufflersMsg decode( FriendlyByteBuf buffer ) {
		
		TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		int dimensionCount = buffer.readInt();
		for( int i = 0; i < dimensionCount; i++ ) {
			ResourceKey<Level> dimension = ResourceKey.create(
				Registry.DIMENSION_REGISTRY,
				buffer.readResourceLocation()
			);
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
	void encode( FriendlyByteBuf buffer ) {
		
		buffer.writeInt( soundMufflers.size() );
		soundMufflers.forEach( ( dimension, soundMufflersSet ) -> {
			buffer.writeResourceLocation( Objects.requireNonNull( dimension.location() ) );
			buffer.writeInt( soundMufflersSet.size() );
			soundMufflersSet.forEach( soundMuffler -> {
				buffer.writeResourceLocation( soundMuffler.getRegistryName() );
				buffer.writeBlockPos( soundMuffler.getPos() );
			} );
		} );
	}
	
	public static void sendToPlayer( ServerPlayer player ) {
		
		TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		Objects.requireNonNull( player.getServer() )
			.getAllLevels()
			.forEach( serverLevel -> {
				TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
				dimensionSoundMufflers.put( serverLevel.dimension(), soundMufflers );
				serverLevel.getCapability( ModCapabilities.SOUND_MUFFLING ).ifPresent(
					soundMufflingCapability -> soundMufflers.addAll( soundMufflingCapability.getSoundMufflers() )
				);
			} );
		Network.CHANNEL.send(
			PacketDistributor.PLAYER.with( () -> player ),
			new InitSoundMufflersMsg( dimensionSoundMufflers )
		);
	}
	
	//package-private
	static void handle( InitSoundMufflersMsg initSoundMufflersMsg, Supplier<NetworkEvent.Context> contextSupplier ) {
		
		SoundMufflersHolder.setDimensionSoundMufflers( initSoundMufflersMsg.soundMufflers );
		contextSupplier.get().setPacketHandled( true );
	}
}
