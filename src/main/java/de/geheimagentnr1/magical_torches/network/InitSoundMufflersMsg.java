package de.geheimagentnr1.magical_torches.network;


import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.TreeMap;
import java.util.TreeSet;


public record InitSoundMufflersMsg(
	@NotNull TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> soundMufflers
) implements CustomPacketPayload {
	
	
	@NotNull
	public static final Type<InitSoundMufflersMsg> TYPE = new Type<>( Network.INIT_SOUND_MUFFLERS_ID );
	
	@NotNull
	public static final StreamCodec<ByteBuf, InitSoundMufflersMsg> STREAM_CODEC = StreamCodec.of(
		InitSoundMufflersMsg::encode,
		InitSoundMufflersMsg::decode
	);
	
	@NotNull
	private static InitSoundMufflersMsg decode( @NotNull ByteBuf buffer ) {
		
		TreeMap<ResourceKey<Level>, TreeSet<SoundMuffler>> dimensionSoundMufflers =
			SoundMufflerHelper.buildDimensionSoundMufflersTreeMap();
		int dimensionCount = ByteBufCodecs.VAR_INT.decode( buffer );
		for( int i = 0; i < dimensionCount; i++ ) {
			ResourceKey<Level> dimension = ResourceKey.create(
				Registries.DIMENSION,
				ResourceLocation.STREAM_CODEC.decode( buffer )
			);
			TreeSet<SoundMuffler> soundMufflers = SoundMufflerHelper.buildSoundMufflersTreeSet();
			dimensionSoundMufflers.put( dimension, soundMufflers );
			int soundMufflersCount = ByteBufCodecs.VAR_INT.decode( buffer );
			for( int j = 0; j < soundMufflersCount; j++ ) {
				soundMufflers.add( SoundMufflingCapability.buildSoundMuffler(
					ResourceLocation.STREAM_CODEC.decode( buffer ),
					net.minecraft.core.BlockPos.STREAM_CODEC.decode( buffer )
				) );
			}
		}
		return new InitSoundMufflersMsg( dimensionSoundMufflers );
	}
	
	private static void encode( @NotNull ByteBuf buffer, @NotNull InitSoundMufflersMsg msg ) {
		
		ByteBufCodecs.VAR_INT.encode( buffer, msg.soundMufflers.size() );
		msg.soundMufflers.forEach( ( dimension, soundMufflersSet ) -> {
			ResourceLocation.STREAM_CODEC.encode( buffer, Objects.requireNonNull( dimension.location() ) );
			ByteBufCodecs.VAR_INT.encode( buffer, soundMufflersSet.size() );
			soundMufflersSet.forEach( soundMuffler -> {
				ResourceLocation.STREAM_CODEC.encode( buffer, soundMuffler.getRegistryName() );
				net.minecraft.core.BlockPos.STREAM_CODEC.encode( buffer, soundMuffler.getPos() );
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
				if( serverLevel.hasData( ModAttachments.SOUND_MUFFLING ) ) {
					var soundMufflingCapability = serverLevel.getData( ModAttachments.SOUND_MUFFLING );
					soundMufflers.addAll( soundMufflingCapability.getSoundMufflers() );
				}
			} );
		PacketDistributor.sendToPlayer( player, new InitSoundMufflersMsg( dimensionSoundMufflers ) );
	}
	
	public static void handle( @NotNull InitSoundMufflersMsg msg, @NotNull IPayloadContext context ) {
		
		context.enqueueWork( () -> {
			SoundMufflersHolder.setDimensionSoundMufflers( msg.soundMufflers );
		} );
	}
	
	@NotNull
	@Override
	public Type<? extends CustomPacketPayload> type() {
		
		return TYPE;
	}
}
