package de.geheimagentnr1.magical_torches.network;


import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;


public class InitSoundMufflersMsg {
	
	
	@NotNull
	private final TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> soundMufflers;
	
	private InitSoundMufflersMsg( @NotNull TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> _soundMufflers ) {
		
		soundMufflers = _soundMufflers;
	}
	
	//package-private
	@NotNull
	static InitSoundMufflersMsg decode( @NotNull FriendlyByteBuf buffer ) {
		
		TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		int dimensionCount = buffer.readInt();
		for( int i = 0; i < dimensionCount; i++ ) {
			ResourceKey<Level> dimension = ResourceKey.create(
				Registries.DIMENSION,
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
	void encode( @NotNull FriendlyByteBuf buffer ) {
		
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
	
	public static void sendToPlayer( @NotNull ServerPlayer player ) {
		
		TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		Objects.requireNonNull( player.getServer() )
			.getAllLevels()
			.forEach( serverLevel -> {
				TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
				dimensionSoundMufflers.put( serverLevel.dimension(), soundMufflers );
				serverLevel.getCapability( ModCapabilitiesRegisterFactory.SOUND_MUFFLING ).ifPresent(
					soundMufflingCapability -> soundMufflers.addAll( soundMufflingCapability.getSoundMufflers() )
				);
			} );
		Network.getInstance().getChannel().send(
			new InitSoundMufflersMsg( dimensionSoundMufflers ),
			PacketDistributor.PLAYER.with( player )
		);
	}
	
	//package-private
	static void handle(
		@NotNull InitSoundMufflersMsg initSoundMufflersMsg,
		@NotNull CustomPayloadEvent.Context contextSupplier ) {
		
		SoundMufflersHolder.setDimensionSoundMufflers( initSoundMufflersMsg.soundMufflers );
		contextSupplier.setPacketHandled( true );
	}
}
