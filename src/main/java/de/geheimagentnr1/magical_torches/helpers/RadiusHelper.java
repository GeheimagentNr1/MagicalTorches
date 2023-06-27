package de.geheimagentnr1.magical_torches.helpers;

import net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;


public class RadiusHelper {
	
	
	public static boolean isEventInRadiusOfBlock( @NotNull BlockPos event_pos, @NotNull BlockPos pos, int range ) {
		
		return pos.getX() - range <= event_pos.getX() && pos.getX() + range >= event_pos.getX() &&
			pos.getY() - range <= event_pos.getY() && pos.getY() + range >= event_pos.getY() &&
			pos.getZ() - range <= event_pos.getZ() && pos.getZ() + range >= event_pos.getZ();
	}
}
