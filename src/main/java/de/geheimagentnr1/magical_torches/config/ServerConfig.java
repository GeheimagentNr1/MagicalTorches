package de.geheimagentnr1.magical_torches.config;

import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ServerConfig {
	
	
	@NotNull
	public static final ModConfigSpec SPEC;
	
	@NotNull
	private static final ModConfigSpec.IntValue ALONE_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.IntValue BAT_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.IntValue SMALL_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.IntValue MEDIUM_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.IntValue GRAND_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.IntValue MEGA_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.ConfigValue<List<? extends String>> HOSTILE_BLOCKED_ENTITIES;
	
	@NotNull
	private static final ModConfigSpec.IntValue SOUND_MUFFLING_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.ConfigValue<List<? extends String>> SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS;
	
	@NotNull
	private static final ModConfigSpec.IntValue CHICKEN_EGG_TORCH_RANGE;
	
	@NotNull
	private static final ModConfigSpec.BooleanValue SHOULD_INVERT_CHICKEN_EGG_BLOCKING;
	
	@Getter
	private static ServerConfig INSTANCE;
	
	static {
		ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
		
		builder.comment( "Config for the spawn blocking torches" ).push( "spawn_blockers" );
		
		ALONE_TORCH_RANGE = builder
			.comment( "Range of the alone torch." )
			.defineInRange( "alone_torch_range", 64, 0, Integer.MAX_VALUE );
		
		BAT_TORCH_RANGE = builder
			.comment( "Range of the bat torch." )
			.defineInRange( "bat_torch_range", 64, 0, Integer.MAX_VALUE );
		
		builder.comment( "Config for hostile mob spawn blocking torches" ).push( "hostile" );
		
		SMALL_TORCH_RANGE = builder
			.comment( "Range of the small torch." )
			.defineInRange( "small_torch_range", 16, 0, Integer.MAX_VALUE );
		
		MEDIUM_TORCH_RANGE = builder
			.comment( "Range of the medium torch." )
			.defineInRange( "medium_torch_range", 32, 0, Integer.MAX_VALUE );
		
		GRAND_TORCH_RANGE = builder
			.comment( "Range of the grand torch." )
			.defineInRange( "grand_torch_range", 64, 0, Integer.MAX_VALUE );
		
		MEGA_TORCH_RANGE = builder
			.comment( "Range of the mega torch." )
			.defineInRange( "mega_torch_range", 128, 0, Integer.MAX_VALUE );
		
		HOSTILE_BLOCKED_ENTITIES = builder
			.comment( "Entities blocked by hostile mob spawn blocking torches" )
			.defineListAllowEmpty(
				"blocked_entities",
				ServerConfig::buildDefaultBlockedEntities,
				() -> "",
				o -> o instanceof String
			);
		
		builder.pop();
		builder.pop();
		
		builder.comment( "Config for the sound muffling torches" ).push( "sound_mufflers" );
		
		SOUND_MUFFLING_TORCH_RANGE = builder
			.comment( "Range of the sound muffling torch." )
			.defineInRange( "sound_muffling_torch_range", 64, 0, Integer.MAX_VALUE );
		
		SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS = builder
			.comment(
				"Sound categories that shall be muffled by the sound muffling torch",
				"Available Sound Categories: " + buildSoundCategories()
			)
			.defineListAllowEmpty(
				"sound_muffling_torch_to_muffle_sounds",
				() -> Stream.of(
						SoundSource.HOSTILE,
						SoundSource.NEUTRAL,
						SoundSource.BLOCKS,
						SoundSource.RECORDS
					).map( SoundSource::name )
					.collect( Collectors.toList() ),
				() -> "",
				o -> {
					if( o instanceof String value ) {
						return Arrays.stream( SoundSource.values() )
							.map( Enum::name )
							.anyMatch( name -> name.equals( value ) );
					}
					return false;
				}
			);
		
		builder.pop();
		
		builder.comment( "Config for the chicken egg torch" ).push( "chicken_egg_torch" );
		
		CHICKEN_EGG_TORCH_RANGE = builder
			.comment( "Range of the chicken egg torch." )
			.defineInRange( "range", 16, 0, Integer.MAX_VALUE );
		
		SHOULD_INVERT_CHICKEN_EGG_BLOCKING = builder
			.comment(
				"If 'false' chicken egg spawning is allowed and is blocked by chicken egg torches.",
				"If 'true' chicken egg spawning is disabled and is enabled by chicken egg torches."
			)
			.define( "should_invert_chicken_egg_blocking", false );
		
		builder.pop();
		
		SPEC = builder.build();
		INSTANCE = new ServerConfig();
	}
	
	@NotNull
	private static List<String> buildDefaultBlockedEntities() {
		
		ArrayList<String> entities = new ArrayList<>();
		BuiltInRegistries.ENTITY_TYPE.forEach( entityType -> {
			if( entityType.getCategory() == MobCategory.MONSTER ) {
				entities.add( BuiltInRegistries.ENTITY_TYPE.getKey( entityType ).toString() );
			}
		} );
		return entities;
	}
	
	@NotNull
	private static String buildSoundCategories() {
		
		return Arrays.stream( SoundSource.values() )
			.map( Enum::name )
			.collect( Collectors.joining( ", " ) );
	}
	
	public int getAloneTorchRange() {
		
		return ALONE_TORCH_RANGE.get();
	}
	
	public int getBatTorchRange() {
		
		return BAT_TORCH_RANGE.get();
	}
	
	@NotNull
	public List<ResourceLocation> getHostileBlockedEntities() {
		
		return HOSTILE_BLOCKED_ENTITIES.get().stream()
			.map( ResourceLocation::tryParse )
			.filter( loc -> loc != null && BuiltInRegistries.ENTITY_TYPE.getOptional( loc ).isPresent() )
			.collect( Collectors.toList() );
	}
	
	public int getSmallTorchRange() {
		
		return SMALL_TORCH_RANGE.get();
	}
	
	public int getMediumTorchRange() {
		
		return MEDIUM_TORCH_RANGE.get();
	}
	
	public int getGrandTorchRange() {
		
		return GRAND_TORCH_RANGE.get();
	}
	
	public int getMegaTorchRange() {
		
		return MEGA_TORCH_RANGE.get();
	}
	
	public int getSoundMufflingTorchRange() {
		
		return SOUND_MUFFLING_TORCH_RANGE.get();
	}
	
	@NotNull
	public List<SoundSource> getSoundMufflingTorchToMuffleSounds() {
		
		return SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS.get().stream()
			.map( SoundSource::valueOf )
			.collect( Collectors.toList() );
	}
	
	public boolean getShouldInvertChickenEggBlocking() {
		
		return SHOULD_INVERT_CHICKEN_EGG_BLOCKING.get();
	}
	
	public int getChickenEggTorchRange() {
		
		return CHICKEN_EGG_TORCH_RANGE.get();
	}
}
