package de.geheimagentnr1.magical_torches.elements.blocks;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.elements.RegistryEntry;
import de.geheimagentnr1.magical_torches.elements.RegistryKeys;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.chicken_egg_spawning.ChickenEggTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling.SoundMufflingTorch;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;


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
	
	public static final List<RegistryEntry<? extends Block>> BLOCKS = List.of(
		//Torches: Chicken Egg Spawning
		RegistryEntry.create( ChickenEggTorch.registry_name, new ChickenEggTorch() ),//BCPFINRLT
		//Torches: Sound Muffeling
		RegistryEntry.create( SoundMufflingTorch.registry_name, new SoundMufflingTorch() ),//BCPFINRLT
		//Torches: Spawn Blocking
		RegistryEntry.create( AloneTorch.registry_name, new AloneTorch() ),//BCPFINRLT
		RegistryEntry.create( BatTorch.registry_name, new BatTorch() ),//BCPFINRLT
		RegistryEntry.create( GrandTorch.registry_name, new GrandTorch() ),//BCPFINRLT
		RegistryEntry.create( MediumTorch.registry_name, new MediumTorch() ),//BCPFINRLT
		RegistryEntry.create( MegaTorch.registry_name, new MegaTorch() ),//BCPFINRLT
		RegistryEntry.create( SmallTorch.registry_name, new SmallTorch() )//BCPFINRLT
	);
	
	//Torches: Chicken Egg Spawning
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS,
		value = MagicalTorches.MODID + ":" + ChickenEggTorch.registry_name )
	public static ChickenEggTorch CHICKEN_EGG_TORCH;
	
	//Torches: Sound Muffeling
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS,
		value = MagicalTorches.MODID + ":" + SoundMufflingTorch.registry_name )
	public static SoundMufflingTorch SOUND_MUFFLING_TORCH;
	
	//Torches: Spawn Blocking
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS, value = MagicalTorches.MODID + ":" + AloneTorch.registry_name )
	public static AloneTorch ALONE_TORCH;
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS, value = MagicalTorches.MODID + ":" + BatTorch.registry_name )
	public static BatTorch BAT_TORCH;
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS, value = MagicalTorches.MODID + ":" + GrandTorch.registry_name )
	public static GrandTorch GRAND_TORCH;
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS, value = MagicalTorches.MODID + ":" + MediumTorch.registry_name )
	public static MediumTorch MEDIUM_TORCH;
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS, value = MagicalTorches.MODID + ":" + MegaTorch.registry_name )
	public static MegaTorch MEGA_TORCH;
	
	@ObjectHolder( registryName = RegistryKeys.BLOCKS, value = MagicalTorches.MODID + ":" + SmallTorch.registry_name )
	public static SmallTorch SMALL_TORCH;
}
