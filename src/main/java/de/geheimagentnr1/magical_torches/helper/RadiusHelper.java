package de.geheimagentnr1.magical_torches.helper;

import net.minecraft.util.math.BlockPos;


public class RadiusHelper {
	
	
	public static boolean isEventInRadiusOfBlock( BlockPos event_pos, BlockPos pos, int range ) {
		
		return pos.getX() - range <= event_pos.getX() && pos.getX() + range >= event_pos.getX() &&
			pos.getY() - range <= event_pos.getY() && pos.getY() + range >= event_pos.getY() &&
			pos.getZ() - range <= event_pos.getZ() && pos.getZ() + range >= event_pos.getZ();
	}
}
