package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class HealthPanel extends JPanel {
	private static final long serialVersionUID = -2257965174196570998L;
	
	private MainPanel main;
	@SuppressWarnings("unused")
	private it.darkfagio.metaexalted.model.PlayCharacter c;
	private JPanel inner;
	private HealthLimbGui[] limb;

	public HealthPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		limb = new HealthLimbGui[6];
		for (int i = 0; i < 6; ++i) {
			limb[i] = new HealthLimbGui(c, i, this);
		}
		remodel();
	}
	
	public void notifyChange() {
		main.notifyChange();
	}

	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
		inner = new JPanel();
		inner.setLayout(new BorderLayout());
		JPanel vitalGrid = new JPanel(), limbGrid = new JPanel();
		vitalGrid.setLayout(new GridLayout(1, 2));
		limbGrid.setLayout(new GridLayout(4,1));
		vitalGrid.add(limb[0]);
		vitalGrid.add(limb[1]);
		for (int i = 2; i < 6; ++i)
			limbGrid.add(limb[i]);
		inner.add(vitalGrid, BorderLayout.NORTH);
		add(limbGrid, BorderLayout.EAST);
		add(inner, BorderLayout.CENTER);
	}

}
