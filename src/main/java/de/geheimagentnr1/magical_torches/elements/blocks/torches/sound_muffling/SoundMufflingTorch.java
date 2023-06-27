package de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling;

import de.geheimagentnr1.magical_torches.MagicalTorches;
import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.BlockWithTooltip;
import de.geheimagentnr1.magical_torches.elements.capabilities.ModCapabilitiesRegisterFactory;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.SoundMufflingCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities.sound_muffling.sound_mufflers.SoundMufflingTorchSoundMuffler;
import de.geheimagentnr1.minecraft_forge_api.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.minecraft_forge_api.util.TranslationKeyHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;


public class SoundMufflingTorch extends BlockWithTooltip implements BlockItemInterface {
	
	
	@NotNull
	public static final String registry_name = "sound_muffling_torch";
	
	@NotNull
	private static final VoxelShape SHAPE = Block.box( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	public SoundMufflingTorch() {
		
		super(
			Properties.of()
				.mapColor( MapColor.WOOD )
				.noCollission()
				.pushReaction( PushReaction.DESTROY )
				.strength( 3 )
				.sound( SoundType.WOOD )
		);
		SoundMufflingCapability.registerSoundMufflers(
			SoundMufflingTorchSoundMuffler.registry_name,
			SoundMufflingTorchSoundMuffler::new
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@NotNull
	@Override
	public VoxelShape getShape(
		@NotNull BlockState state,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull CollisionContext context ) {
		
		return SHAPE;
	}
	
	@SuppressWarnings( "deprecation" )
	@NotNull
	@Override
	public VoxelShape getCollisionShape(
		@NotNull BlockState state,
		@NotNull BlockGetter level,
		@NotNull BlockPos pos,
		@NotNull CollisionContext context ) {
		
		return Shapes.empty();
	}
	
	@NotNull
	@Override
	public MutableComponent getInformation() {
		
		ServerConfig serverConfig = ServerConfig.getINSTANCE();
		return Component.translatable(
			TranslationKeyHelper.generateTooltipTranslationKey( MagicalTorches.MODID, "sound_muffling" ),
			serverConfig.getSoundMufflingTorchRange(),
			SoundMufflingTorchSoundMuffler.FACTORY.build( BlockPos.ZERO ).getSoundCategoriesString( serverConfig )
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onPlace(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState oldState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilitiesRegisterFactory.SOUND_MUFFLING ).ifPresent(
			capability -> capability.addSoundMuffler(
				level.dimension(),
				new SoundMufflingTorchSoundMuffler( pos )
			)
		);
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onRemove(
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState newState,
		boolean isMoving ) {
		
		level.getCapability( ModCapabilitiesRegisterFactory.SOUND_MUFFLING ).ifPresent(
			capability -> capability.removeSoundMuffler(
				level.dimension(),
				new SoundMufflingTorchSoundMuffler( pos )
			)
		);
		super.onRemove( state, level, pos, newState, isMoving );
	}
}
