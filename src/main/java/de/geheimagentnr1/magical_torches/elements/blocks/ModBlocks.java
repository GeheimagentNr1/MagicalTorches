package de.geheimagentnr1.magical_torches.elements.blocks;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.GrandTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.MediumTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.MegaTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.SmallTorch;
import net.minecraft.block.Block;
import net.minecraftforge.registries.ObjectHolder;


@SuppressWarnings( { "PublicField", "PublicStaticArrayField", "StaticNonFinalField", "unused" } )
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
	
	public final static Block[] BLOCKS = new Block[] {
		new GrandTorch(),//BCPFINRLT
		new MediumTorch(),//BCPFINRLT
		new MegaTorch(),//BCPFINRLT
		new SmallTorch(),//BCPFINRLT
	};
	
	@ObjectHolder( MagicalTorches.MODID + ":" + GrandTorch.registry_name )
	public static GrandTorch GRAND_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + MediumTorch.registry_name )
	public static MediumTorch MEDIUM_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + MegaTorch.registry_name )
	public static MegaTorch MEGA_TORCH;
	
	@ObjectHolder( MagicalTorches.MODID + ":" + SmallTorch.registry_name )
	public static SmallTorch SMALL_TORCH;
}
