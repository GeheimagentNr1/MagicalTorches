package de.geheimagentnr1.magical_torches.elements.creative_mod_tabs;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;


public class ModCreativeModeTabs {
	
	
	@NotNull
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
		DeferredRegister.create( Registries.CREATIVE_MODE_TAB, MagicalTorches.MODID );
	
	@NotNull
	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAGICAL_TORCHES_TAB = CREATIVE_MODE_TABS.register(
		MagicalTorches.MODID,
		() -> CreativeModeTab.builder()
			.title( Component.translatable( "itemGroup." + MagicalTorches.MODID ) )
			.icon( () -> new ItemStack( ModBlocks.MEGA_TORCH.get() ) )
			.displayItems( ( parameters, output ) -> {
				output.accept( ModBlocks.CHICKEN_EGG_TORCH.get() );
				output.accept( ModBlocks.SOUND_MUFFLING_TORCH.get() );
				output.accept( ModBlocks.ALONE_TORCH.get() );
				output.accept( ModBlocks.BAT_TORCH.get() );
				output.accept( ModBlocks.SMALL_TORCH.get() );
				output.accept( ModBlocks.MEDIUM_TORCH.get() );
				output.accept( ModBlocks.GRAND_TORCH.get() );
				output.accept( ModBlocks.MEGA_TORCH.get() );
			} )
			.build()
	);
	
	public static void register( @NotNull IEventBus modEventBus ) {
		
		CREATIVE_MODE_TABS.register( modEventBus );
	}
}
