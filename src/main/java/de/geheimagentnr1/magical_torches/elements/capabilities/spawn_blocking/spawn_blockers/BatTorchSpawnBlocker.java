package de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.spawn_blockers;

import de.geheimagentnr1.magical_torches.config.ServerConfig;
import de.geheimagentnr1.magical_torches.elements.blocks.torches.spawn_blocking.BatTorch;
import de.geheimagentnr1.magical_torches.elements.capabilities.spawn_blocking.SpawnBlocker;
import de.geheimagentnr1.magical_torches.helpers.ResourceLocationBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;


public class BatTorchSpawnBlocker extends SpawnBlocker {
	
	
	@NotNull
	private static final Identifier BAT_ID = Identifier.withDefaultNamespace( "bat" );
	
	@NotNull
	public static final Identifier registry_name = ResourceLocationBuilder.build( BatTorch.registry_name );
	
	public BatTorchSpawnBlocker( @NotNull BlockPos _pos ) {
		
		super( _pos );
	}
	
	@NotNull
	@Override
	public Identifier getRegistryName() {
		
		return registry_name;
	}
	
	@Override
	public int getRange() {
		
		return ServerConfig.getINSTANCE().getBatTorchRange();
	}
	
	@Override
	public boolean shouldBlockEntity( @NotNull Entity entity ) {
		
		//EntityType.BAT moved to EntityTypes.BAT in 26.3, the registry id is the same in all versions
		return BAT_ID.equals( BuiltInRegistries.ENTITY_TYPE.getKey( entity.getType() ) );
	}
}
