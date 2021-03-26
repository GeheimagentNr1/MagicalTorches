package de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockRenderTypeInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilities;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.sound_mufflers.SoundMufflingTorchSoundMuffler;
import de.geheimagentnr1.magical_torches.helpers.TranslationKeyHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class SoundMufflingTorch extends BlockWithTooltip implements BlockItemInterface, BlockRenderTypeInterface {
	
	
	public static final String registry_name = "sound_muffling_torch";
	
	private static final VoxelShape SHAPE = Block.makeCuboidShape( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	public SoundMufflingTorch() {
		
		super( AbstractBlock.Properties.create( Material.WOOD )
			.doesNotBlockMovement()
			.hardnessAndResistance( 3 )
			.sound( SoundType.WOOD ) );
		setRegistryName( registry_name );
		SoundMufflingCapability.registerSoundMufflers(
			SoundMufflingTorchSoundMuffler.registry_name,
			SoundMufflingTorchSoundMuffler::new
		);
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.getCutout();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape(
		@Nonnull BlockState state,
		@Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos,
		@Nonnull ISelectionContext context ) {
		
		return SHAPE;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape(
		@Nonnull BlockState state,
		@Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos,
		@Nonnull ISelectionContext context ) {
		
		return VoxelShapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public PushReaction getPushReaction( @Nonnull BlockState state ) {
		
		return PushReaction.DESTROY;
	}
	
	@Override
	public TextComponent getInformation() {
		
		return new TranslationTextComponent(
			TranslationKeyHelper.buildTooltipTranslationKey( "sound_muffling" ),
			ServerConfig.getSoundMufflingTorchRange(),
			SoundMufflingTorchSoundMuffler.FACTORY.build( BlockPos.ZERO ).getSoundCategoriesString()
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onBlockAdded(
		@Nonnull BlockState state,
		@Nonnull World worldIn,
		@Nonnull BlockPos pos,
		@Nonnull BlockState oldState,
		boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.SOUND_MUFFLING ).ifPresent(
			capability -> capability.addSoundMuffler(
				worldIn.getDimensionKey(),
				new SoundMufflingTorchSoundMuffler( pos )
			) );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onReplaced(
		@Nonnull BlockState state,
		@Nonnull World worldIn,
		@Nonnull BlockPos pos,
		@Nonnull BlockState newState,
		boolean isMoving ) {
		
		worldIn.getCapability( ModCapabilities.SOUND_MUFFLING ).ifPresent( capability -> capability.removeSoundMuffler(
			worldIn.getDimensionKey(),
			new SoundMufflingTorchSoundMuffler( pos )
		) );
		super.onReplaced( state, worldIn, pos, newState, isMoving );
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.SOUND_MUFFLING_TORCH, properties, registry_name );
	}
}
