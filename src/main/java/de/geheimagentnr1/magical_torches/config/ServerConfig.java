package de.geheimagentnr1.magical_torches.config;

import de.geheimagentnr1.minecraft_forge_api.AbstractMod;
import de.geheimagentnr1.minecraft_forge_api.config.AbstractConfig;
import lombok.Getter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.config.ModConfig;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ServerConfig extends AbstractConfig {
	
	
	@NotNull
	private static final String SPAWN_BLOCKERS_KEY = "spawn_blockers";
	
	@NotNull
	private static final List<String> ALONE_TORCH_RANGE_KEY = List.of( SPAWN_BLOCKERS_KEY, "alone_torch_range" );
	
	@NotNull
	private static final List<String> BAT_TORCH_RANGE_KEY = List.of( SPAWN_BLOCKERS_KEY, "bat_torch_range" );
	
	@NotNull
	private static final String HOSTILE_KEY = "hostile";
	
	@NotNull
	private static final List<String> SMALL_TORCH_RANGE_KEY = List.of(
		SPAWN_BLOCKERS_KEY,
		HOSTILE_KEY,
		"small_torch_range"
	);
	
	@NotNull
	private static final List<String> MEDIUM_TORCH_RANGE_KEY = List.of(
		SPAWN_BLOCKERS_KEY,
		HOSTILE_KEY,
		"medium_torch_range"
	);
	
	@NotNull
	private static final List<String> GRAND_TORCH_RANGE_KEY = List.of(
		SPAWN_BLOCKERS_KEY,
		HOSTILE_KEY,
		"grand_torch_range"
	);
	
	@NotNull
	private static final List<String> MEGA_TORCH_RANGE_KEY = List.of(
		SPAWN_BLOCKERS_KEY,
		HOSTILE_KEY,
		"mega_torch_range"
	);
	
	@NotNull
	private static final List<String> HOSTILE_BLOCKED_ENTITIES_KEY = List.of(
		SPAWN_BLOCKERS_KEY,
		HOSTILE_KEY,
		"blocked_entities"
	);
	
	@NotNull
	private static final String SOUND_MUFFLERS_KEY = "sound_mufflers";
	
	@NotNull
	private static final List<String> SOUND_MUFFLING_TORCH_RANGE_KEY = List.of(
		SOUND_MUFFLERS_KEY,
		"sound_muffling_torch_range"
	);
	
	@NotNull
	private static final List<String> SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS_KEY = List.of(
		SOUND_MUFFLERS_KEY,
		"sound_muffling_torch_to_muffle_sounds"
	);
	
	@NotNull
	private static final String CHICKEN_EGG_TORCH_KEY = "chicken_egg_torch";
	
	@NotNull
	private static final List<String> CHICKEN_EGG_TORCH_RANGE_KEY = List.of( CHICKEN_EGG_TORCH_KEY, "range" );
	
	@NotNull
	private static final List<String> SHOULD_INVERT_CHICKEN_EGG_BLOCKING_KEY = List.of(
		CHICKEN_EGG_TORCH_KEY,
		"should_invert_chicken_egg_blocking"
	);
	
	@Getter
	private static ServerConfig INSTANCE;
	
	@NotNull
	private List<ResourceLocation> hostileBlockedEntities = new ArrayList<>();
	
	@NotNull
	private List<SoundSource> soundMufflingTorchToMuffleSounds = new ArrayList<>();
	
	public ServerConfig( @NotNull AbstractMod _abstractMod ) {
		
		super( _abstractMod );
		INSTANCE = this;
	}
	
	@NotNull
	@Override
	public ModConfig.Type type() {
		
		return ModConfig.Type.SERVER;
	}
	
	@Override
	public boolean isEarlyLoad() {
		
		return false;
	}
	
	@Override
	protected void registerConfigValues() {
		
		push( "Config for the spawn blocking torches", SPAWN_BLOCKERS_KEY );
		registerConfigValue(
			"Range of the alone torch.",
			ALONE_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 64, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			"Range of the bat torch.",
			BAT_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 64, 0, Integer.MAX_VALUE )
		);
		push( "Config for hostile mob spawn blocking torches", HOSTILE_KEY );
		registerConfigValue(
			"Range of the small torch.",
			SMALL_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 16, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			"Range of the medium torch.",
			MEDIUM_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 32, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			"Range of the grand torch.",
			GRAND_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 64, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			"Range of the mega torch.",
			MEGA_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 128, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			"Entities blocked by hostile mob spawn blocking torches",
			HOSTILE_BLOCKED_ENTITIES_KEY,
			buildBlockedEntities()
		);
		pop();
		pop();
		push( "Config for the sound muffling torches", SOUND_MUFFLERS_KEY );
		registerConfigValue(
			"Range of the sound muffling torch.",
			SOUND_MUFFLING_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 64, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			List.of(
				"Sound categories that shall be muffled by the sound muffling torch",
				"Available Sound Categories: " + buildSoundCategories()
			),
			SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS_KEY,
			( builder, path ) -> builder.define(
				path,
				Stream.of(
						SoundSource.HOSTILE,
						SoundSource.NEUTRAL,
						SoundSource.BLOCKS,
						SoundSource.RECORDS
					).map( SoundSource::name )
					.collect( Collectors.toList() ),
				o -> {
					if( o instanceof List<?> values ) {
						List<String> avaiableValues = Arrays.stream( SoundSource.values() )
							.map( Enum::name )
							.toList();
						for( Object value : values ) {
							if( !( value instanceof String ) || !avaiableValues.contains( (String)value ) ) {
								return false;
							}
						}
						return true;
					}
					return false;
				}
			)
		);
		pop();
		push( "Config for the chicken egg torch", CHICKEN_EGG_TORCH_KEY );
		registerConfigValue(
			"Range of the chicken egg torch.",
			CHICKEN_EGG_TORCH_RANGE_KEY,
			( builder, path ) -> builder.defineInRange( path, 16, 0, Integer.MAX_VALUE )
		);
		registerConfigValue(
			List.of(
				"If 'false' chicken egg spawning is allowed and is blocked by chicken egg torches.",
				"If 'true' chicken egg spawning is disabled and is enabled by chicken egg torches."
			),
			SHOULD_INVERT_CHICKEN_EGG_BLOCKING_KEY,
			false
		);
		pop();
	}
	
	@NotNull
	private List<String> buildBlockedEntities() {
		
		ArrayList<String> entities = new ArrayList<>();
		BuiltInRegistries.ENTITY_TYPE.forEach( entityType -> {
			if( entityType.getCategory() == MobCategory.MONSTER ) {
				entities.add( BuiltInRegistries.ENTITY_TYPE.getKey( entityType ).toString() );
			}
		} );
		return entities;
	}
	
	@NotNull
	private String buildSoundCategories() {
		
		return Arrays.stream( SoundSource.values() )
			.map( Enum::name )
			.collect( Collectors.joining( ", " ) );
	}
	
	@Override
	protected void handleConfigChanging() {
		
		loadHostileBlockedEntities();
		loadSoundMufflingTorchToMuffleSounds();
	}
	
	private void loadHostileBlockedEntities() {
		
		List<String> hostileBlockedEntitiesValue = getHostileBlockedEntitiesValue();
		List<ResourceLocation> hostile_blocked_entities = new ArrayList<>();
		boolean changed = false;
		for( int i = 0; i < hostileBlockedEntitiesValue.size(); i++ ) {
			ResourceLocation resourceLocation = ResourceLocation.tryParse( hostileBlockedEntitiesValue.get( i ) );
			if( resourceLocation == null || BuiltInRegistries.ENTITY_TYPE.getOptional( resourceLocation ).isEmpty() ) {
				hostileBlockedEntitiesValue.remove( i );
				i--;
				changed = true;
			} else {
				hostile_blocked_entities.add( resourceLocation );
			}
		}
		if( changed ) {
			setHostileBlockedEntitiesValue( hostileBlockedEntitiesValue );
		}
		hostileBlockedEntities = hostile_blocked_entities;
	}
	
	private void loadSoundMufflingTorchToMuffleSounds() {
		
		soundMufflingTorchToMuffleSounds = getSoundMufflingTorchToMuffleSoundsValue()
			.stream()
			.map( SoundSource::valueOf )
			.collect( Collectors.toList() );
	}
	
	public int getAloneTorchRange() {
		
		return getValue( Integer.class, ALONE_TORCH_RANGE_KEY );
	}
	
	public int getBatTorchRange() {
		
		return getValue( Integer.class, BAT_TORCH_RANGE_KEY );
	}
	
	@NotNull
	private List<String> getHostileBlockedEntitiesValue() {
		
		return getListValue( String.class, HOSTILE_BLOCKED_ENTITIES_KEY );
	}
	
	private void setHostileBlockedEntitiesValue( @NotNull List<String> value ) {
		
		setListValue( String.class, HOSTILE_BLOCKED_ENTITIES_KEY, value );
	}
	
	@NotNull
	public List<ResourceLocation> getHostileBlockedEntities() {
		
		return hostileBlockedEntities;
	}
	
	public int getSmallTorchRange() {
		
		return getValue( Integer.class, SMALL_TORCH_RANGE_KEY );
	}
	
	public int getMediumTorchRange() {
		
		return getValue( Integer.class, MEDIUM_TORCH_RANGE_KEY );
	}
	
	public int getGrandTorchRange() {
		
		return getValue( Integer.class, GRAND_TORCH_RANGE_KEY );
	}
	
	public int getMegaTorchRange() {
		
		return getValue( Integer.class, MEGA_TORCH_RANGE_KEY );
	}
	
	public int getSoundMufflingTorchRange() {
		
		return getValue( Integer.class, SOUND_MUFFLING_TORCH_RANGE_KEY );
	}
	
	@NotNull
	private List<String> getSoundMufflingTorchToMuffleSoundsValue() {
		
		return getListValue( String.class, SOUND_MUFFLING_TORCH_TO_MUFFLE_SOUNDS_KEY );
	}
	
	@NotNull
	public List<SoundSource> getSoundMufflingTorchToMuffleSounds() {
		
		return soundMufflingTorchToMuffleSounds;
	}
	
	public boolean getShouldInvertChickenEggBlocking() {
		
		return getValue( Boolean.class, SHOULD_INVERT_CHICKEN_EGG_BLOCKING_KEY );
	}
	
	public int getChickenEggTorchRange() {
		
		return getValue( Integer.class, CHICKEN_EGG_TORCH_RANGE_KEY );
	}
}
