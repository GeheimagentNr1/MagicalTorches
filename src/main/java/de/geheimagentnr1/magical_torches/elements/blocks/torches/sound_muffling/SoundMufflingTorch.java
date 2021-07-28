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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;


public class SoundMufflingTorch extends BlockWithTooltip implements BlockItemInterface, BlockRenderTypeInterface {
	
	
	public static final String registry_name = "sound_muffling_torch";
	
	private static final VoxelShape SHAPE = Block.box( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	public SoundMufflingTorch() {
		
		super( Properties.of( Material.WOOD ).noCollission().strength( 3 ).sound( SoundType.WOOD ) );
		setRegistryName( registry_name );
		SoundMufflingCapability.registerSoundMufflers(
			SoundMufflingTorchSoundMuffler.registry_name,
			SoundMufflingTorchSoundMuffler::new
		);
	}
	
	@Override
	public RenderType getRenderType() {
		
		return RenderType.cutout();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape(
		@Nonnull BlockState state,
		@Nonnull BlockGetter level,
		@Nonnull BlockPos pos,
		@Nonnull CollisionContext context ) {
		
		return SHAPE;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape(
		@Nonnull BlockState state,
		@Nonnull BlockGetter level,
		@Nonnull BlockPos pos,
		@Nonnull CollisionContext context ) {
		
		return Shapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public PushReaction getPistonPushReaction( @Nonnull BlockState state ) {
		
		return PushReaction.DESTROY;
	}
	
	@Override
	public MutableComponent getInformation() {
		
		return new TranslatableComponent(
			TranslationKeyHelper.buildTooltipTranslationKey( "sound_muffling" ),
			ServerConfig.getSoundMufflingTorchRange(),
			SoundMufflingTorchSoundMuffler.FACTORY.build( BlockPos.ZERO ).getSoundCategoriesString()
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onPlace(
		@Nonnull BlockState state,
		@Nonnull Level level,
		@Nonnull BlockPos pos,
		@Nonnull BlockState oldState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilities.SOUND_MUFFLING ).ifPresent(
			capability -> capability.addSoundMuffler( level.dimension(), new SoundMufflingTorchSoundMuffler( pos ) )
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onRemove(
		@Nonnull BlockState state,
		@Nonnull Level level,
		@Nonnull BlockPos pos,
		@Nonnull BlockState newState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilities.SOUND_MUFFLING ).ifPresent(
			capability -> capability.removeSoundMuffler( level.dimension(), new SoundMufflingTorchSoundMuffler( pos ) )
		);
		super.onRemove( state, level, pos, newState, isMoving );
	}
	
	@SuppressWarnings( "ParameterHidesMemberVariable" )
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.SOUND_MUFFLING_TORCH, properties, registry_name );
	}
}
