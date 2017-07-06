package it.darkfagio.metaexalted.simple;

import java.net.URL;

import it.darkfagio.metaexalted.MetaExalted;
import it.darkfagio.metaexalted.utils.Images;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WillpowerLevel extends JLabel {
	private static final long serialVersionUID = 1L;

	public WillpowerLevel(){
		String url = (Images.WILLPOWER_LEVEL);
		URL inUrl = MetaExalted.class.getResource(url);
		setIcon(new ImageIcon(inUrl));	}

}
