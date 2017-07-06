package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	MainPanel main;
	it.darkfagio.metaexalted.model.PlayCharacter c;
	
	public BackgroundPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		setLayout(new BorderLayout());
		add(new JLabel("Background Panel"), BorderLayout.CENTER);
	}

}
