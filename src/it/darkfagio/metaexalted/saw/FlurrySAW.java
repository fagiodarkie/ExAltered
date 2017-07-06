package it.darkfagio.metaexalted.saw;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import it.darkfagio.utils.Format;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FlurrySAW extends SAWPanel {
	private static final long serialVersionUID = 1L;
	private SolveAttackWizard saw;
	private JTextField[] values;

	public FlurrySAW(SolveAttackWizard solveAttackWizard) {
		saw = solveAttackWizard;
		remodel();
	}

	public void remodel() {
		removeAll();
		
		setLayout(new FlowLayout());
		JPanel inner = new JPanel(new BorderLayout()), labels = new JPanel(new GridLayout(3, 1)),
				texts = new JPanel(new GridLayout(3,1));
		values = new JTextField[3];
		String[] def = {"1", "", "0"};
		String[] lab = {"Numero di attacchi: ", "Penalità massima: ", "Moti spesi: "};
		for (int i = 0; i < 3; ++i) {
			values[i] = Format.getNumericTextField(def[i]);
			texts.add(values[i]);
			labels.add(new JLabel(lab[i]));
		}
		inner.add(labels, BorderLayout.CENTER);
		inner.add(texts, BorderLayout.EAST);
		add(inner);
		
		revalidate();
		
		
	}

	@Override
	public void nextInvoked() {
		int n = Integer.parseInt(values[0].getText());
		saw.setValue(SolveAttackWizard.attackNumber, n);
		for (int i = 0; i < n; ++i)
			saw.addPane(SolveAttackWizard.SIMPLE_ATTACK, null);
		if (values[1].getText().trim().length() > 0)
			saw.setValue(SolveAttackWizard.maxAttackPenalty, Integer.parseInt(values[1].getText()));
		saw.setValue(SolveAttackWizard.spentMotes, Integer.parseInt(values[2].getText()));
	}

}
