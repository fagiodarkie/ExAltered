package it.darkfagio.metaexalted.utils;

import it.darkfagio.metaexalted.MetaExalted;

import java.net.URL;

import javax.swing.ImageIcon;

public class Images {
	public static final String
		AGGRAVATED = "/AggravatedDamage.png",
		BASHING = "/BashingDamage.png",
		LETHAL = "/LethalDamage.png",
		NO_DAMAGE = "/NoDamage.png",
		WILLPOWER_LEVEL = "/WillpowerLevel.png";
	
	public static ImageIcon getIcon(String url) {
		URL inUrl = MetaExalted.class.getResource(url);
		return new ImageIcon(inUrl);
	}
}
