package de.geheimagentnr1.magical_torches.helpers;

import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;


public class PushReactionHelper {
	
	
	//The PushReaction constants were renamed in 26.3 (DESTROY -> POPPED), the constant is looked up by name so one
	//jar covers 26.1 - 26.3: blocks with this push reaction are destroyed by pistons
	@NotNull
	public static final PushReaction DESTROY = Arrays.stream( PushReaction.values() )
		.filter( reaction -> List.of( "DESTROY", "POPPED" ).contains( reaction.name() ) )
		.findFirst()
		.orElseThrow();
}
