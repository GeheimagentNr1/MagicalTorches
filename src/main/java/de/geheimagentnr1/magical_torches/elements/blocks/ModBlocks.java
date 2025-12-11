package de.geheimagentnr1.magical_torches.elements.blocks;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning.ChickenEggTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling.SoundMufflingTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;


public class ModBlocks {
	
	
	@NotNull
	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks( MagicalTorches.MODID );
	
	@NotNull
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems( MagicalTorches.MODID );
	
	// Torches: Chicken Egg Spawning
	@NotNull
	public static final DeferredBlock<ChickenEggTorch> CHICKEN_EGG_TORCH = registerBlockWithItem(
		ChickenEggTorch.registry_name,
		ChickenEggTorch::new
	);
	
	// Torches: Sound Muffling
	@NotNull
	public static final DeferredBlock<SoundMufflingTorch> SOUND_MUFFLING_TORCH = registerBlockWithItem(
		SoundMufflingTorch.registry_name,
		SoundMufflingTorch::new
	);
	
	// Torches: Spawn Blocking
	@NotNull
	public static final DeferredBlock<AloneTorch> ALONE_TORCH = registerBlockWithItem(
		AloneTorch.registry_name,
		AloneTorch::new
	);
	
	@NotNull
	public static final DeferredBlock<BatTorch> BAT_TORCH = registerBlockWithItem(
		BatTorch.registry_name,
		BatTorch::new
	);
	
	@NotNull
	public static final DeferredBlock<GrandTorch> GRAND_TORCH = registerBlockWithItem(
		GrandTorch.registry_name,
		GrandTorch::new
	);
	
	@NotNull
	public static final DeferredBlock<MediumTorch> MEDIUM_TORCH = registerBlockWithItem(
		MediumTorch.registry_name,
		MediumTorch::new
	);
	
	@NotNull
	public static final DeferredBlock<MegaTorch> MEGA_TORCH = registerBlockWithItem(
		MegaTorch.registry_name,
		MegaTorch::new
	);
	
	@NotNull
	public static final DeferredBlock<SmallTorch> SMALL_TORCH = registerBlockWithItem(
		SmallTorch.registry_name,
		SmallTorch::new
	);
	
	private static <T extends Block> DeferredBlock<T> registerBlockWithItem(
		@NotNull String name,
		@NotNull Supplier<T> blockSupplier ) {
		
		DeferredBlock<T> block = BLOCKS.register( name, blockSupplier );
		ITEMS.register( name, () -> new BlockItem( block.get(), new Item.Properties() ) );
		return block;
	}
	
	public static void register( @NotNull IEventBus modEventBus ) {
		
		BLOCKS.register( modEventBus );
		ITEMS.register( modEventBus );
	}
}
