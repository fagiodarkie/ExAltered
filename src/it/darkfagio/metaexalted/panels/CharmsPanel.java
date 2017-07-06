package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CharmsPanel extends JPanel {
	private static final long serialVersionUID = -8765303198365181990L;

	MainPanel main;
	it.darkfagio.metaexalted.model.PlayCharacter c;
	
	
	public CharmsPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		setLayout(new BorderLayout());
		add(new JLabel("Charms Panel"), BorderLayout.CENTER);
	}

}
