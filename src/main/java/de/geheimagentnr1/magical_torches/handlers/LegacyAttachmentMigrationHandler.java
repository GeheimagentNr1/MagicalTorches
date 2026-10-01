package de.geheimagentnr1.magical_torches.handlers;

import com.mojang.logging.LogUtils;
import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.TagValueInput;
import net.minecraft.world.level.storage.ValueInput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;


//Since 26.1 NeoForge stores the level attachments in dimensions/<namespace>/<path>/data/neoforge/data_attachments.dat
//instead of <old dimension folder>/data/neoforge_data_attachments.dat and the world upgrade does not move the old file, so torches of worlds of older Minecraft
//versions would stop working. When a level is loaded and the new file does not contain the torch data yet, the data
//of the old file is taken over once: up to 1.21.5 as list (INBTSerializable<ListTag>), since 1.21.6 as compound
//(ValueIOSerializable). The level attachments are loaded before LevelEvent.Load and saved to the new file afterwards.
public class LegacyAttachmentMigrationHandler {
	
	
	@NotNull
	private static final Logger LOGGER = LogUtils.getLogger();
	
	@NotNull
	private static final String LEGACY_ATTACHMENTS_FILE = "neoforge_data_attachments.dat";
	
	@NotNull
	private static final String ATTACHMENTS_FILE = "neoforge/data_attachments.dat";
	
	@SubscribeEvent
	public void handleLevelLoadEvent( @NotNull LevelEvent.Load event ) {
		
		if( !( event.getLevel() instanceof ServerLevel level ) ) {
			return;
		}
		Path worldFolder = level.getServer().getWorldPath( LevelResource.ROOT );
		CompoundTag legacyAttachments = readAttachments(
			level,
			getLegacyDimensionFolder( level.dimension(), worldFolder ).resolve( "data" ).resolve( LEGACY_ATTACHMENTS_FILE )
		);
		if( legacyAttachments.isEmpty() ) {
			return;
		}
		CompoundTag attachments = readAttachments(
			level,
			DimensionType.getStorageFolder( level.dimension(), worldFolder ).resolve( "data" ).resolve( ATTACHMENTS_FILE )
		);
		migrate( level, legacyAttachments, attachments, "spawn_blocking",
			nbt -> level.getData( ModAttachments.SPAWN_BLOCKING ).deserializeLegacy( nbt ),
			input -> level.getData( ModAttachments.SPAWN_BLOCKING ).deserialize( input ) );
		migrate( level, legacyAttachments, attachments, "sound_muffling",
			nbt -> level.getData( ModAttachments.SOUND_MUFFLING ).deserializeLegacy( nbt ),
			input -> level.getData( ModAttachments.SOUND_MUFFLING ).deserialize( input ) );
		migrate( level, legacyAttachments, attachments, "chicken_egg_spawning",
			nbt -> level.getData( ModAttachments.CHICKEN_EGG_SPAWNING ).deserializeLegacy( nbt ),
			input -> level.getData( ModAttachments.CHICKEN_EGG_SPAWNING ).deserialize( input ) );
	}
	
	//Dimension folders up to 1.21.11. The 26.1 world upgrade moves the vanilla files into dimensions/<namespace>/<path>,
	//but leaves NeoForge's neoforge_data_attachments.dat in the old folders.
	@NotNull
	private static Path getLegacyDimensionFolder( @NotNull ResourceKey<Level> dimension, @NotNull Path worldFolder ) {
		
		if( dimension == Level.OVERWORLD ) {
			return worldFolder;
		}
		if( dimension == Level.NETHER ) {
			return worldFolder.resolve( "DIM-1" );
		}
		if( dimension == Level.END ) {
			return worldFolder.resolve( "DIM1" );
		}
		return dimension.identifier().resolveAgainst( worldFolder.resolve( "dimensions" ) );
	}
	
	@NotNull
	private static CompoundTag readAttachments( @NotNull ServerLevel level, @NotNull Path file ) {
		
		if( !Files.exists( file ) ) {
			return new CompoundTag();
		}
		try {
			return NbtIo.readCompressed( file, NbtAccounter.unlimitedHeap() ).getCompoundOrEmpty( "data" );
		} catch( IOException exception ) {
			LOGGER.error( "Failed to read the level attachments {} of {}", file, level.dimension(), exception );
			return new CompoundTag();
		}
	}
	
	private static void migrate(
		@NotNull ServerLevel level,
		@NotNull CompoundTag legacyAttachments,
		@NotNull CompoundTag attachments,
		@NotNull String name,
		@NotNull Consumer<ListTag> listDeserializer,
		@NotNull Consumer<ValueInput> valueInputDeserializer ) {
		
		String key = MagicalTorches.MODID + ":" + name;
		if( attachments.contains( key ) ) {
			return;
		}
		if( legacyAttachments.get( key ) instanceof ListTag nbt ) {
			listDeserializer.accept( nbt );
			LOGGER.info( "Migrated {} {} entries of {} to the new save format", nbt.size(), key, level.dimension() );
		} else if( legacyAttachments.get( key ) instanceof CompoundTag nbt ) {
			valueInputDeserializer.accept( TagValueInput.create( ProblemReporter.DISCARDING, level.registryAccess(), nbt ) );
			LOGGER.info( "Migrated {} of {} to the new save file", key, level.dimension() );
		}
	}
}
