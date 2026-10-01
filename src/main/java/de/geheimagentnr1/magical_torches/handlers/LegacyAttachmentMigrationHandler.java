package de.geheimagentnr1.magical_torches.handlers;

import com.mojang.logging.LogUtils;
import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModAttachments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.function.Consumer;


//Up to 1.21.5 the level attachments were stored as INBTSerializable<ListTag>. Since 1.21.6 NeoForge only reads
//compounds (ValueIOSerializable), so the old lists would be lost and placed torches would stop working.
//When a level is loaded, the old lists are read once from the saved attachment file. The level attachments
//are loaded before LevelEvent.Load and are saved in the new format afterwards, so this only happens once.
public class LegacyAttachmentMigrationHandler {


	@NotNull
	private static final Logger LOGGER = LogUtils.getLogger();

	@NotNull
	private static final String ATTACHMENTS_FILE_NAME = "neoforge_data_attachments";

	@SubscribeEvent
	public void handleLevelLoadEvent( @NotNull LevelEvent.Load event ) {

		if( !( event.getLevel() instanceof ServerLevel level ) ) {
			return;
		}
		CompoundTag attachments;
		try {
			attachments = level.getDataStorage().readTagFromDisk( ATTACHMENTS_FILE_NAME, null, 0 )
				.getCompoundOrEmpty( "data" );
		} catch( NoSuchFileException exception ) {
			return;
		} catch( IOException exception ) {
			LOGGER.error( "Failed to read the level attachments of {}", level.dimension(), exception );
			return;
		}
		migrate( level, attachments, "spawn_blocking", nbt -> level.getData( ModAttachments.SPAWN_BLOCKING )
			.deserializeLegacy( nbt ) );
		migrate( level, attachments, "sound_muffling", nbt -> level.getData( ModAttachments.SOUND_MUFFLING )
			.deserializeLegacy( nbt ) );
		migrate( level, attachments, "chicken_egg_spawning", nbt -> level.getData( ModAttachments.CHICKEN_EGG_SPAWNING )
			.deserializeLegacy( nbt ) );
	}

	private static void migrate(
		@NotNull ServerLevel level,
		@NotNull CompoundTag attachments,
		@NotNull String name,
		@NotNull Consumer<ListTag> deserializer ) {

		String key = MagicalTorches.MODID + ":" + name;
		if( attachments.get( key ) instanceof ListTag nbt ) {
			deserializer.accept( nbt );
			LOGGER.info( "Migrated {} {} entries of {} to the new save format", nbt.size(), key, level.dimension() );
		}
	}
}
