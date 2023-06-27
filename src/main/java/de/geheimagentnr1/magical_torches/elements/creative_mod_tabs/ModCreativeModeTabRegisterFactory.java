package de.geheimagentnr1.magical_torches.elements.creative_mod_tabs;

import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocksRegisterFactory;
import de.geheimagentnr1.minecraft_forge_api.elements.creative_mod_tabs.CreativeModeTabFactory;
import de.geheimagentnr1.minecraft_forge_api.elements.creative_mod_tabs.CreativeModeTabRegisterFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;


@RequiredArgsConstructor
public class ModCreativeModeTabRegisterFactory extends CreativeModeTabRegisterFactory {
	
	
	@NotNull
	private final ModBlocksRegisterFactory modBlocksRegisterFactory;
	
	@NotNull
	@Override
	protected List<CreativeModeTabFactory> factories() {
		
		return List.of(
			new MagicalTorchesCreativeModeTabFactory( modBlocksRegisterFactory )
		);
	}
}
