package it.darkfagio.metaexalted.saw;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class StartSAW extends SAWPanel {
	private static final long serialVersionUID = 1L;
	private SolveAttackWizard saw;
	private JRadioButton atk, def;

	public StartSAW(SolveAttackWizard solveAttackWizard) {
		saw = solveAttackWizard;
		remodel();
	}

	private void remodel() {
		removeAll();
		
		setLayout(new BorderLayout());
		
		JPanel inner = new JPanel(new GridLayout(2, 1, 20, 20)), center = new JPanel(new FlowLayout());
		atk = new JRadioButton("Attacca");
		def = new JRadioButton("Difenditi");
		ButtonGroup g = new ButtonGroup();
		g.add(atk); g.add(def);
		atk.setSelected(true);
		
		inner.setPreferredSize(new Dimension(200, 100));
		inner.add(atk); inner.add(def);
		inner.setBorder(BorderFactory.createTitledBorder("Cosa vuoi fare?"));
		center.add(inner);
		add(center, BorderLayout.CENTER);
		
		revalidate();
	}

	@Override
	public void nextInvoked() {
		saw.setState(SolveAttackWizard.backwardEnabled, true);
		saw.setState(SolveAttackWizard.forwardEnabled, true);
		saw.setState(SolveAttackWizard.attacking, atk.isSelected());
		saw.addPane((atk.isSelected() ? SolveAttackWizard.ATTACK : SolveAttackWizard.DEFEND), null);
	}

	
}
