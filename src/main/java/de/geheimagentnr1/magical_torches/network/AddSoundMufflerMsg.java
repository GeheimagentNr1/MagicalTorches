package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.TreeSet;


public class AddSoundMufflerMsg {
	
	
	@NotNull
	private final ResourceLocation dimensionRegistryName;
	
	@NotNull
	private final ResourceLocation soundMufflerRegistryName;
	
	@NotNull
	private final BlockPos pos;
	
	private AddSoundMufflerMsg(
		@NotNull ResourceLocation _dimensionRegistryName,
		@NotNull ResourceLocation _soundMufflerRegistryName,
		@NotNull BlockPos _pos ) {
		
		dimensionRegistryName = _dimensionRegistryName;
		soundMufflerRegistryName = _soundMufflerRegistryName;
		pos = _pos;
	}
	
	//package-private
	@NotNull
	static AddSoundMufflerMsg decode( @NotNull FriendlyByteBuf buffer ) {
		
		return new AddSoundMufflerMsg(
			buffer.readResourceLocation(),
			buffer.readResourceLocation(),
			buffer.readBlockPos()
		);
	}
	
	//package-private
	void encode( @NotNull FriendlyByteBuf buffer ) {
		
		buffer.writeResourceLocation( dimensionRegistryName );
		buffer.writeResourceLocation( soundMufflerRegistryName );
		buffer.writeBlockPos( pos );
	}
	
	public static void sendToAll( @NotNull ResourceLocation dimension, @NotNull SoundMuffler soundMuffler ) {
		
		Network.getInstance().getChannel().send(
			new AddSoundMufflerMsg( dimension, soundMuffler.getRegistryName(), soundMuffler.getPos() ),
			PacketDistributor.ALL.noArg()
		);
	}
	
	//package-private
	static void handle(
		@NotNull AddSoundMufflerMsg addSoundMufflerMsg,
		@NotNull CustomPayloadEvent.Context contextSupplier ) {
		
		ResourceKey<Level> dimension = ResourceKey.create(
			Registries.DIMENSION,
			addSoundMufflerMsg.dimensionRegistryName
		);
		TreeSet<SoundMuffler> list = SoundMufflersHolder.getDimensionSoundMufflers( dimension )
			.orElse( SoundMufflerHelper.buildSoundMufflersTreeSet() );
		list.add( SoundMufflingCapability.buildSoundMuffler(
			addSoundMufflerMsg.soundMufflerRegistryName,
			addSoundMufflerMsg.pos
		) );
		SoundMufflersHolder.getDimensionSoundMufflers().put( dimension, list );
		contextSupplier.setPacketHandled( true );
	}
}
