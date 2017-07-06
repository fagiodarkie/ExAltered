package it.darkfagio.metaexalted.simple;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import it.darkfagio.metaexalted.panels.WillpowerGridPanel;
import it.darkfagio.metaexalted.utils.Images;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class WillpowerBox extends JLabel {
	private static final long serialVersionUID = 1L;
	private ImageIcon actual, alternative;
	private WillpowerGridPanel w;
	private boolean used;

	public WillpowerBox(WillpowerGridPanel willpowerGridPanel, boolean b) {
		w = willpowerGridPanel;
		used = b;
		String url1 = Images.LETHAL, url2 = Images.NO_DAMAGE;
		actual = Images.getIcon((b ? url1 : url2));
		alternative = Images.getIcon((b ? url2 : url1));
		setIcon(actual);
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				setIcon(actual);
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				setIcon(alternative);
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (used) w.restoreWillpower();
				else w.consumeWillpower();
				w.notifyChange();
			}
		});
	}

}
