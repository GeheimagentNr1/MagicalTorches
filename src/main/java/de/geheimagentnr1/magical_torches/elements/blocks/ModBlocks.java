package de.geheimagentnr1.magical_torches.elements.blocks;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning.ChickenEggTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling.SoundMufflingTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.*;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ObjectHolder;


@SuppressWarnings( { "PublicField", "PublicStaticArrayField", "StaticNonFinalField" } )
public class ModBlocks {
	
	//TODO:
	// B - Block Textur fertig
	// C - Cullface korrekt
	// P - Partikel fertig
	// F - Funktion fertig
	// I - Item fertig
	// N - Name und Registierungsname vorhanden und fertig
	// R - Rezept fertig
	// L - Loottable fertig
	// T - Tags fertig
	
	public static final Block[] BLOCKS = new Block[] {
		//Torches: Chicken Egg Spawning
		new ChickenEggTorch(),//BCPFINRLT
		//Torches: Sound Muffeling
		new SoundMufflingTorch(),//BCPFINRLT
		//Torches: Spawn Blocking
		new AloneTorch(),//BCPFINRLT
		new BatTorch(),//BCPFINRLT
		new GrandTorch(),//BCPFINRLT
		new MediumTorch(),//BCPFINRLT
		new MegaTorch(),//BCPFINRLT
		new SmallTorch(),//BCPFINRLT
	};
	
	//Torches: Chicken Egg Spawning
	
	@ObjectHolder( MagicalTorches.MODID + ":" + ChickenEggTorch.registry_name )
	public static ChickenEggTorch CHICKEN_EGG_TORCH;
	
	//Torches: Sound Muffeling
	
	@ObjectHolder( MagicalTorches.MODID + ":" + SoundMufflingTorch.registry_name )
	public static SoundMufflingTorch SOUND_MUFFLING_TORCH;
	
	//Torches: Spawn Blocking
	
	@ObjectHolder( MagicalTorches.MODID + ":" + AloneTorch.registry_name )
	public static AloneTorch ALONE_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + BatTorch.registry_name )
	public static BatTorch BAT_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + GrandTorch.registry_name )
	public static GrandTorch GRAND_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + MediumTorch.registry_name )
	public static MediumTorch MEDIUM_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + MegaTorch.registry_name )
	public static MegaTorch MEGA_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + SmallTorch.registry_name )
	public static SmallTorch SMALL_TORCH;
}
