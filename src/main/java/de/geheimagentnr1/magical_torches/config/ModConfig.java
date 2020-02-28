package de.geheimagentnr1.magical_torches.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import de.geheimagentnr1.magical_torches.MagicalTorches;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ModConfig {
	
	
	private final static Logger LOGGER = LogManager.getLogger();
	
	private final static String mod_name = "Magical Torches";
	
	private final static ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	private final static ForgeConfigSpec CONFIG;
	
	private final static ForgeConfigSpec.IntValue ALONE_TORCH_RANGE;
	
	private final static ForgeConfigSpec.IntValue BAT_TORCH_RANGE;
	
	private final static ForgeConfigSpec.IntValue SMALL_TORCH_RANGE;
	
	private final static ForgeConfigSpec.IntValue MEDIUM_TORCH_RANGE;
	
	private final static ForgeConfigSpec.IntValue GRAND_TORCH_RANGE;
	
	private final static ForgeConfigSpec.IntValue MEGA_TORCH_RANGE;
	
	private final static ForgeConfigSpec.IntValue SOUND_MUFFLING_TORCH_RANGE;
	
	static {
		
		BUILDER.comment( "Config for the spawn blocking torches" ).push( "spawn_blockers" );
		ALONE_TORCH_RANGE = BUILDER.comment( "Range of the alone torch." )
			.defineInRange( "alone_torch_range", 64, 0, Integer.MAX_VALUE );
		BAT_TORCH_RANGE = BUILDER.comment( "Range of the bat torch." )
			.defineInRange( "bat_torch_range", 64, 0, Integer.MAX_VALUE );
		SMALL_TORCH_RANGE = BUILDER.comment( "Range of the small torch." )
			.defineInRange( "small_torch_range", 16, 0, Integer.MAX_VALUE );
		MEDIUM_TORCH_RANGE = BUILDER.comment( "Range of the medium torch." )
			.defineInRange( "medium_torch_range", 32, 0, Integer.MAX_VALUE );
		GRAND_TORCH_RANGE = BUILDER.comment( "Range of the grand torch." )
			.defineInRange( "grand_torch_range", 64, 0, Integer.MAX_VALUE );
		MEGA_TORCH_RANGE = BUILDER.comment( "Range of the mega torch." )
			.defineInRange( "mega_torch_range", 128, 0, Integer.MAX_VALUE );
		BUILDER.comment( "Config for the sound muffling torches" ).push( "spawn_mufflers" );
		SOUND_MUFFLING_TORCH_RANGE = BUILDER.comment( "Range of the sound muffling torch." )
			.defineInRange( "mega_torch_range", 64, 0, Integer.MAX_VALUE );
		
		CONFIG = BUILDER.build();
	}
	
	public static void load() {
		
		CommentedFileConfig configData = CommentedFileConfig.builder( FMLPaths.CONFIGDIR.get().resolve(
			MagicalTorches.MODID + ".toml" ) ).sync().autosave().writingMode( WritingMode.REPLACE ).build();
		
		LOGGER.info( "Loading \"{}\" Config", mod_name );
		configData.load();
		CONFIG.setConfig( configData );
		LOGGER.info( "Spawn Blockers" );
		LOGGER.info( "{} = {}", ALONE_TORCH_RANGE.getPath(), ALONE_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", BAT_TORCH_RANGE.getPath(), BAT_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", SMALL_TORCH_RANGE.getPath(), SMALL_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", MEDIUM_TORCH_RANGE.getPath(), MEDIUM_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", GRAND_TORCH_RANGE.getPath(), GRAND_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", MEGA_TORCH_RANGE.getPath(), MEGA_TORCH_RANGE.get() );
		LOGGER.info( "Sound Mufflers" );
		LOGGER.info( "{} = {}", SOUND_MUFFLING_TORCH_RANGE.getPath(), SOUND_MUFFLING_TORCH_RANGE.get() );
		LOGGER.info( "\"{}\" Config loaded", mod_name );
	}
	
	public static int getAloneTorchRange() {
		
		return ALONE_TORCH_RANGE.get();
	}
	
	public static int getBatTorchRange() {
		
		return BAT_TORCH_RANGE.get();
	}
	
	public static int getSmallTorchRange() {
		
		return SMALL_TORCH_RANGE.get();
	}
	
	public static int getMediumTorchRange() {
		
		return MEDIUM_TORCH_RANGE.get();
	}
	
	public static int getGrandTorchRange() {
		
		return GRAND_TORCH_RANGE.get();
	}
	
	public static int getMegaTorchRange() {
		
		return MEGA_TORCH_RANGE.get();
	}
	
	public static int getSoundMufflingTorchRange() {
		
		return SOUND_MUFFLING_TORCH_RANGE.get();
	}
}
