package de.geheimagentnr1.magical_torches.network;

import de.geheimagentnr1.magical_torches.config.SoundMufflersHolder;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMuffler;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.helpers.SoundMufflerHelper;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

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
	static AddSoundMufflerMsg decode( PacketBuffer buffer ) {
		
		return new AddSoundMufflerMsg(
			buffer.readResourceLocation(),
			buffer.readResourceLocation(),
			buffer.readBlockPos()
		);
	}
	
	//package-private
	void encode( PacketBuffer buffer ) {
		
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
		
		RegistryKey<World> dimension = RegistryKey.getOrCreateKey(
			Registry.WORLD_KEY,
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
