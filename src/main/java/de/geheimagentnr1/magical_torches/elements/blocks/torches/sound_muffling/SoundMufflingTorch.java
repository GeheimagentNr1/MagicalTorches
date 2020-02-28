package de.geheimagentnr1.magical_torches.elements.blocks.torches.sound_muffling;

import de.geheimagentnr1.magical_torches.elements.blocks.BlockItemInterface;
import de.geheimagentnr1.magical_torches.elements.blocks.ModBlocks;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.SoundMufflingClientCapability;
import de.geheimagentnr1.magical_torches.elements.capabilities_client.sound_muffling.sound_mufflers.SoundMufflingTorchSoundMuffler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;


public class SoundMufflingTorch extends Block implements BlockItemInterface {
	
	
	public final static String registry_name = "sound_muffling_torch";
	
	private final static VoxelShape SHAPE = Block.makeCuboidShape( 6.5, 0, 6.5, 9.5, 10, 9.5 );
	
	public SoundMufflingTorch() {
		
		super( Block.Properties.create( Material.WOOD ).hardnessAndResistance( 3 ).sound( SoundType.WOOD ) );
		setRegistryName( registry_name );
	}
	
	/**
	 * Called throughout the code as a replacement for block instanceof BlockContainer
	 * Moving this to the Block base class allows for mods that wish to extend vanilla
	 * blocks, and also want to have a tile entity on that block, may.
	 *
	 * Return true from this function to specify this block has a tile entity.
	 *
	 * @param state State of the current block
	 * @return True if block has a tile entity, false otherwise
	 */
	@Override
	public boolean hasTileEntity( BlockState state ) {
		
		return true;
	}
	
	/**
	 * Called throughout the code as a replacement for ITileEntityProvider.createNewTileEntity
	 * Return the same thing you would from that function.
	 * This will fall back to ITileEntityProvider.createNewTileEntity(World) if this block is a ITileEntityProvider
	 *
	 * @param state The state of the current block
	 * @param world The world to create the TE in
	 * @return A instance of a class extending TileEntity
	 */
	@Nullable
	@Override
	public TileEntity createTileEntity( BlockState state, IBlockReader world ) {
		
		return new SoundMufflingTorchTile();
	}
	
	@Nonnull
	@Override
	public BlockRenderLayer getRenderLayer() {
		
		return BlockRenderLayer.CUTOUT;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getShape( BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context ) {
		
		return SHAPE;
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public VoxelShape getCollisionShape( @Nonnull BlockState state, @Nonnull IBlockReader worldIn,
		@Nonnull BlockPos pos, ISelectionContext context ) {
		
		return VoxelShapes.empty();
	}
	
	@SuppressWarnings( "deprecation" )
	@Nonnull
	@Override
	public PushReaction getPushReaction( BlockState state ) {
		
		return PushReaction.DESTROY;
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onBlockAdded( BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving ) {
		
		SoundMufflingClientCapability.addSoundMuffler(
			SoundMufflingTorchSoundMuffler.FACTORY.buildSoundMuffler( pos ) );
	}
	
	@SuppressWarnings( "deprecation" )
	@Override
	public void onReplaced( BlockState state, @Nonnull World worldIn, @Nonnull BlockPos pos,
		@Nonnull BlockState newState, boolean isMoving ) {
		
		SoundMufflingClientCapability.removeSoundMuffler(
			SoundMufflingTorchSoundMuffler.FACTORY.buildSoundMuffler( pos ) );
		super.onReplaced( state, worldIn, pos, newState, isMoving );
	}
	
	@Override
	public Item getBlockItem( Item.Properties properties ) {
		
		return createBlockItem( ModBlocks.SOUND_MUFFLING_TORCH, properties, registry_name );
	}
}
