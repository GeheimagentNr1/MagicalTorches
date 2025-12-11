package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.TreeSet;


public record RemoveSoundMufflerMsg(
	@NotNull ResourceLocation dimensionRegistryName,
	@NotNull ResourceLocation soundMufflerRegistryName,
	@NotNull BlockPos pos
) implements CustomPacketPayload {
	
	
	@NotNull
	public static final Type<RemoveSoundMufflerMsg> TYPE = new Type<>( Network.REMOVE_SOUND_MUFFLER_ID );
	
	@NotNull
	public static final StreamCodec<ByteBuf, RemoveSoundMufflerMsg> STREAM_CODEC = StreamCodec.of(
		RemoveSoundMufflerMsg::encode,
		RemoveSoundMufflerMsg::decode
	);
	
	@NotNull
	private static RemoveSoundMufflerMsg decode( @NotNull ByteBuf buffer ) {
		
		return new RemoveSoundMufflerMsg(
			ResourceLocation.STREAM_CODEC.decode( buffer ),
			ResourceLocation.STREAM_CODEC.decode( buffer ),
			BlockPos.STREAM_CODEC.decode( buffer )
		);
	}
	
	private static void encode( @NotNull ByteBuf buffer, @NotNull RemoveSoundMufflerMsg msg ) {
		
		ResourceLocation.STREAM_CODEC.encode( buffer, msg.dimensionRegistryName );
		ResourceLocation.STREAM_CODEC.encode( buffer, msg.soundMufflerRegistryName );
		BlockPos.STREAM_CODEC.encode( buffer, msg.pos );
	}
	
	public static void sendToAll( @NotNull ResourceLocation dimension, @NotNull SoundMuffler soundMuffler ) {
		
		PacketDistributor.sendToAllPlayers(
			new RemoveSoundMufflerMsg( dimension, soundMuffler.getRegistryName(), soundMuffler.getPos() )
		);
	}
	
	public static void handle( @NotNull RemoveSoundMufflerMsg msg, @NotNull IPayloadContext context ) {
		
		context.enqueueWork( () -> {
			ResourceKey<Level> dimension = ResourceKey.create(
				Registries.DIMENSION,
				msg.dimensionRegistryName
			);
			TreeSet<SoundMuffler> list = SoundMufflersHolder.getDimensionSoundMufflers( dimension )
				.orElse( SoundMufflerHelper.buildSoundMufflersTreeSet() );
			list.remove( SoundMufflingCapability.buildSoundMuffler(
				msg.soundMufflerRegistryName,
				msg.pos
			) );
			SoundMufflersHolder.getDimensionSoundMufflers().put( dimension, list );
		} );
	}
	
	@NotNull
	@Override
	public Type<? extends CustomPacketPayload> type() {
		
		return TYPE;
	}
}
