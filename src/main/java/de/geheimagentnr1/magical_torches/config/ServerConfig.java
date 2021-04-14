package de.geheimagentnr1.magical_torches.config;

import net.minecraft.entity.EntityClassification;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class ServerConfig {
	
	
	private static final Logger LOGGER = LogManager.getLogger( ServerConfig.class );
	
	private static final String MOD_NAME = ModLoadingContext.get().getActiveContainer().getModInfo().getDisplayName();
	
	private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
	
	public static final ForgeConfigSpec CONFIG;
	
	private static final ForgeConfigSpec.IntValue ALONE_TORCH_RANGE;
	
	private static final ForgeConfigSpec.IntValue BAT_TORCH_RANGE;
	
	private static final ForgeConfigSpec.IntValue SMALL_TORCH_RANGE;
	
	private static final ForgeConfigSpec.IntValue MEDIUM_TORCH_RANGE;
	
	private static final ForgeConfigSpec.IntValue GRAND_TORCH_RANGE;
	
	private static final ForgeConfigSpec.IntValue MEGA_TORCH_RANGE;
	
	private static final ForgeConfigSpec.ConfigValue<List<String>> HOSTILE_BLOCKED_ENTITIES;
	
	private static final ForgeConfigSpec.IntValue SOUND_MUFFLING_TORCH_RANGE;
	
	private static final ForgeConfigSpec.ConfigValue<List<String>> SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS;
	
	private static final ForgeConfigSpec.IntValue CHICKEN_EGG_TORCH_RANGE;
	
	private static final ForgeConfigSpec.BooleanValue SHOULD_INVERT_CHICKEN_EGG_BLOCKING;
	
	private static List<ResourceLocation> hostileBlockedEntities = new ArrayList<>();
	
	private static List<SoundCategory> soundMufflingTorchToMuffleSounds = new ArrayList<>();
	
	static {
		
		BUILDER.comment( "Config for the spawn blocking torches" )
			.push( "spawn_blockers" );
		ALONE_TORCH_RANGE = BUILDER.comment( "Range of the alone torch." )
			.defineInRange( "alone_torch_range", 64, 0, Integer.MAX_VALUE );
		BAT_TORCH_RANGE = BUILDER.comment( "Range of the bat torch." )
			.defineInRange( "bat_torch_range", 64, 0, Integer.MAX_VALUE );
		BUILDER.comment( "Config for hostile mob spawn blocking torches" )
			.push( "hostile" );
		SMALL_TORCH_RANGE = BUILDER.comment( "Range of the small torch." )
			.defineInRange( "small_torch_range", 16, 0, Integer.MAX_VALUE );
		MEDIUM_TORCH_RANGE = BUILDER.comment( "Range of the medium torch." )
			.defineInRange( "medium_torch_range", 32, 0, Integer.MAX_VALUE );
		GRAND_TORCH_RANGE = BUILDER.comment( "Range of the grand torch." )
			.defineInRange( "grand_torch_range", 64, 0, Integer.MAX_VALUE );
		MEGA_TORCH_RANGE = BUILDER.comment( "Range of the mega torch." )
			.defineInRange( "mega_torch_range", 128, 0, Integer.MAX_VALUE );
		HOSTILE_BLOCKED_ENTITIES = BUILDER.comment( "Entities blocked by hostile mob spawn blocking torches" )
			.define( "blocked_entities", buildBlockedEntities() );
		BUILDER.pop();
		BUILDER.pop();
		BUILDER.comment( "Config for the sound muffling torches" )
			.push( "sound_mufflers" );
		SOUND_MUFFLING_TORCH_RANGE = BUILDER.comment( "Range of the sound muffling torch." )
			.defineInRange( "sound_muffling_torch_range", getDefaultSoundMufflingTorchRange(), 0, Integer.MAX_VALUE );
		SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS = BUILDER.comment( String.format(
			"Sound categories that shall be muffled by the sound muffling torch%nAvailable Sound Categories: %s",
			buildSoundCategories()
		) ).define(
			"sound_muffling_torch_to_muffle_sounds",
			getDefaultSoundMufflingTorchToMuffleSounds().stream()
				.map( SoundCategory::name )
				.collect( Collectors.toList() ),
			o -> {
				if( o instanceof String ) {
					String value = (String)o;
					for( SoundCategory soundCategory : SoundCategory.values() ) {
						if( soundCategory.name().equals( value ) ) {
							return true;
						}
					}
				}
				return false;
			}
		);
		BUILDER.pop();
		BUILDER.comment( "Config for the chicken egg torch" )
			.push( "chicken_egg_torch" );
		CHICKEN_EGG_TORCH_RANGE = BUILDER.comment( "Range of the chicken egg torch." )
			.defineInRange( "range", 16, 0, Integer.MAX_VALUE );
		SHOULD_INVERT_CHICKEN_EGG_BLOCKING = BUILDER.comment( String.format(
			"If 'false' chicken egg spawning is allowed and is blocked by chicken egg torches.%n" +
				"If 'true' chicken egg spawning is disabled and is enabled by chicken egg torches." ) )
			.define( "should_invert_chicken_egg_blocking", false );
		BUILDER.pop();
		CONFIG = BUILDER.build();
	}
	
	private static List<String> buildBlockedEntities() {
		
		ArrayList<String> entities = new ArrayList<>();
		Registry.ENTITY_TYPE.forEach( entityType -> {
			if( entityType.getClassification() == EntityClassification.MONSTER ) {
				entities.add( Objects.requireNonNull( entityType.getRegistryName() ).toString() );
			}
		} );
		return entities;
	}
	
	//package-private
	static List<SoundCategory> getDefaultSoundMufflingTorchToMuffleSounds() {
		
		return Arrays.asList(
			SoundCategory.HOSTILE,
			SoundCategory.NEUTRAL,
			SoundCategory.BLOCKS,
			SoundCategory.RECORDS
		);
	}
	
	//package-private
	@SuppressWarnings( "SameReturnValue" )
	static int getDefaultSoundMufflingTorchRange() {
		
		return 64;
	}
	
	private static String buildSoundCategories() {
		
		return Arrays.stream( SoundCategory.values() )
			.map( Enum::name )
			.collect( Collectors.joining( ", " ) );
	}
	
	public static void analyseAndPrintConfig() {
		
		loadHostileBlockedEntities();
		loadSoundMufflingTorchToMuffleSounds();
		printConfig();
	}
	
	private static void loadHostileBlockedEntities() {
		
		List<String> hostileBlockedEntitiesValue = HOSTILE_BLOCKED_ENTITIES.get();
		List<ResourceLocation> hostile_blocked_entities = new ArrayList<>();
		boolean changed = false;
		for( int i = 0; i < hostileBlockedEntitiesValue.size(); i++ ) {
			ResourceLocation resourceLocation = ResourceLocation.tryCreate( hostileBlockedEntitiesValue.get( i ) );
			if( resourceLocation == null || !Registry.ENTITY_TYPE.getOptional( resourceLocation ).isPresent() ) {
				hostileBlockedEntitiesValue.remove( i );
				i--;
				changed = true;
			} else {
				hostile_blocked_entities.add( resourceLocation );
			}
		}
		if( changed ) {
			HOSTILE_BLOCKED_ENTITIES.set( hostileBlockedEntitiesValue );
		}
		hostileBlockedEntities = hostile_blocked_entities;
	}
	
	private static void loadSoundMufflingTorchToMuffleSounds() {
		
		soundMufflingTorchToMuffleSounds = SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS.get()
			.stream()
			.map( SoundCategory::valueOf )
			.collect( Collectors.toList() );
	}
	
	private static void printConfig() {
		
		LOGGER.info( "Loading \"{}\" Config", MOD_NAME );
		LOGGER.info( "{} = {}", ALONE_TORCH_RANGE.getPath(), ALONE_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", BAT_TORCH_RANGE.getPath(), BAT_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", SMALL_TORCH_RANGE.getPath(), SMALL_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", MEDIUM_TORCH_RANGE.getPath(), MEDIUM_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", GRAND_TORCH_RANGE.getPath(), GRAND_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", MEGA_TORCH_RANGE.getPath(), MEGA_TORCH_RANGE.get() );
		LOGGER.info( "{} = {}", HOSTILE_BLOCKED_ENTITIES.getPath(), HOSTILE_BLOCKED_ENTITIES.get() );
		LOGGER.info( "{} = {}", SOUND_MUFFLING_TORCH_RANGE.getPath(), SOUND_MUFFLING_TORCH_RANGE.get() );
		LOGGER.info(
			"{} = {}",
			SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS.getPath(),
			SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS.get()
		);
		LOGGER.info( "{} = {}", CHICKEN_EGG_TORCH_RANGE.getPath(), CHICKEN_EGG_TORCH_RANGE.get() );
		LOGGER.info(
			"{} = {}",
			SHOULD_INVERT_CHICKEN_EGG_BLOCKING.getPath(),
			SHOULD_INVERT_CHICKEN_EGG_BLOCKING.get()
		);
		LOGGER.info( "\"{}\" Config loaded", MOD_NAME );
	}
	
	public static int getAloneTorchRange() {
		
		return ALONE_TORCH_RANGE.get();
	}
	
	public static int getBatTorchRange() {
		
		return BAT_TORCH_RANGE.get();
	}
	
	public static List<ResourceLocation> getHostileBlockedEntities() {
		
		return hostileBlockedEntities;
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
	
	public static List<SoundCategory> getSoundMufflingTorchToMuffleSounds() {
		
		return soundMufflingTorchToMuffleSounds;
	}
	
	public static boolean getShouldInvertChickenEggBlocking() {
		
		return SHOULD_INVERT_CHICKEN_EGG_BLOCKING.get();
	}
	
	public static int getChickenEggTorchRange() {
		
		return CHICKEN_EGG_TORCH_RANGE.get();
	}
}
