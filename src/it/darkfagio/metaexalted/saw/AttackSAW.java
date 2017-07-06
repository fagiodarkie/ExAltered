package it.darkfagio.metaexalted.saw;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AttackSAW extends SAWPanel {
	private static final long serialVersionUID = 1L;
	private SolveAttackWizard saw;
	private JRadioButton[] radios;
	private ButtonGroup g;
	private final int options = 6;
	private final int[] map = {SolveAttackWizard.SIMPLE_ATTACK, SolveAttackWizard.FLURRY, SolveAttackWizard.CHARM,
			SolveAttackWizard.COMBO, SolveAttackWizard.COUNTER, SolveAttackWizard.INTERCEPT};

	public AttackSAW(SolveAttackWizard solveAttackWizard) {
		saw = solveAttackWizard;
		remodel();
	}

	public void remodel() {
		removeAll();
		
		setLayout(new FlowLayout());
		JPanel inner = new JPanel(new GridLayout(options, 1, 10, 10));
		radios = new JRadioButton[options];
		String[] names = {"Attacco Semplice", "Turbine", "Prodigio", "Combo", "Contrattacco", "Intercetto"};
		g = new ButtonGroup();
		for (int i = 0; i < options; ++i) {
			radios[i] = new JRadioButton(names[i]);
			g.add(radios[i]);
			inner.add(radios[i]);
		}
		radios[0].setSelected(true);
		
		add(inner);
		revalidate();
	}

	public void nextInvoked() {
		int p = -1;
		for (int i = 0; i < options; ++i) {
			if (radios[i].isSelected()) {
				p = map[i];
				break;
			}
		}
		if (p > 0) saw.addPane(p, null);
	}

}
