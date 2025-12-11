package de.geheimagentnr1.magical_torches.elements.capabilities;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.capabilities.chicken_egg_spawning.ChickenEggSpawningCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlockingCapability;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class ModAttachments {
	
	
	@NotNull
	public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
		DeferredRegister.create( NeoForgeRegistries.ATTACHMENT_TYPES, MagicalTorches.MODID );
	
	@NotNull
	public static final Supplier<AttachmentType<SpawnBlockingCapability>> SPAWN_BLOCKING = ATTACHMENT_TYPES.register(
		"spawn_blocking",
		() -> AttachmentType.serializable( SpawnBlockingCapability::new ).build()
	);
	
	@NotNull
	public static final Supplier<AttachmentType<SoundMufflingCapability>> SOUND_MUFFLING = ATTACHMENT_TYPES.register(
		"sound_muffling",
		() -> AttachmentType.serializable( SoundMufflingCapability::new ).build()
	);
	
	@NotNull
	public static final Supplier<AttachmentType<ChickenEggSpawningCapability>> CHICKEN_EGG_SPAWNING = ATTACHMENT_TYPES.register(
		"chicken_egg_spawning",
		() -> AttachmentType.serializable( () -> new ChickenEggSpawningCapability( ServerConfig.getINSTANCE() ) ).build()
	);
	
	public static void register( @NotNull IEventBus modEventBus ) {
		
		ATTACHMENT_TYPES.register( modEventBus );
	}
}
