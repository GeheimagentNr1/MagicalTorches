package de.geheimagentnr1.magical_torches.elements.blocks;

import net.minecraft.client.renderer.RenderType;


public interface BlockRenderTypeInterface {
	
	//public
	default RenderType getRenderType() {
		
		return RenderType.getSolid();
	}
}
