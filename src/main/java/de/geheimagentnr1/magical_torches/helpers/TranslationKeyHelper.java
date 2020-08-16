package de.geheimagentnr1.magical_torches.helpers;

import de.geheimagentnr1.magical_torches.MagicalTorches;


public class TranslationKeyHelper {
	
	
	public static String buildTooltipTranslationKey( String key ) {
		
		return "tooltip." + MagicalTorches.MODID + "." + key;
	}
}
