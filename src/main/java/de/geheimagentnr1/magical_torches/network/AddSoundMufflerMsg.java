package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.PacketDistributor;

import java.util.TreeSet;
import java.util.function.Supplier;


public class AddSoundMufflerMsg {
	
	
	private final ResourceLocation dimensionRegistryName;
	
	private final ResourceLocation soundMufflerRegistryName;
	
	private final BlockPos pos;
	
	private AddSoundMufflerMsg(
		ResourceLocation _dimensionRegistryName,
		ResourceLocation _soundMufflerRegistryName,
		BlockPos _pos ) {
		
		dimensionRegistryName = _dimensionRegistryName;
		soundMufflerRegistryName = _soundMufflerRegistryName;
		pos = _pos;
	}
	
	//package-private
	static AddSoundMufflerMsg decode( FriendlyByteBuf buffer ) {
		
		return new AddSoundMufflerMsg(
			buffer.readResourceLocation(),
			buffer.readResourceLocation(),
			buffer.readBlockPos()
		);
	}
	
	//package-private
	void encode( FriendlyByteBuf buffer ) {
		
		buffer.writeResourceLocation( dimensionRegistryName );
		buffer.writeResourceLocation( soundMufflerRegistryName );
		buffer.writeBlockPos( pos );
	}
	
	public static void sendToAll( ResourceLocation dimension, SoundMuffler soundMuffler ) {
		
		Network.CHANNEL.send(
			PacketDistributor.ALL.noArg(),
			new AddSoundMufflerMsg( dimension, soundMuffler.getRegistryName(), soundMuffler.getPos() )
		);
	}
	
	//package-private
	static void handle( AddSoundMufflerMsg addSoundMufflerMsg, Supplier<NetworkEvent.Context> contextSupplier ) {
		
		ResourceKey<Level> dimension = ResourceKey.create(
			Registry.DIMENSION_REGISTRY,
			addSoundMufflerMsg.dimensionRegistryName
		);
		TreeSet<SoundMuffler> list = SoundMufflersHolder.getDimensionSoundMufflers( dimension )
			.orElse( SoundMufflerHelper.buildSoundMufflersTreeSet() );
		list.add( SoundMufflingCapability.buildSoundMuffler(
			addSoundMufflerMsg.soundMufflerRegistryName,
			addSoundMufflerMsg.pos
		) );
		SoundMufflersHolder.getDimensionSoundMufflers().put( dimension, list );
		contextSupplier.get().setPacketHandled( true );
	}
}
