package de.geheimagentnr1.magical_torches;

import de.geheimagentnr1.magical_torches.config.MainConfig;
import de.geheimagentnr1.magical_torches.setup.ModSetup;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;


@SuppressWarnings( "UtilityClassWithPublicConstructor" )
@Mod( MagicalTorches.MODID )
public class MagicalTorches {
	
	
	public static final String MODID = "magical_torches";
	
	public static final ModSetup setup = new ModSetup();
	
	public MagicalTorches() {
		
		ModLoadingContext.get().registerConfig( ModConfig.Type.COMMON, MainConfig.CONFIG, MODID + ".toml" );
	}
}
