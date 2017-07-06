package it.darkfagio.metaexalted.wizard;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.CharModifyPanel;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.Format;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AbilityModifierWizard extends CharModifyPanel {
	private static final long serialVersionUID = 1L;
	JLabel[] attrLabel;
	JTextField[] attrVals;
	PlayCharacter c;
	FirstStepCreationWizard w;

	public AbilityModifierWizard(FirstStepCreationWizard wizardDialog, final PlayCharacter c) {
		w = wizardDialog;
		this.c = c;
		attrLabel = new JLabel[25];
		attrVals = new JTextField[25];
		
		setLayout(new BorderLayout());
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(25, 2, 10, 10));
		
		for (int i = 0; i < 25; ++i) {
			attrLabel[i] = new JLabel();
			attrVals[i] = Format.getNumericTextField();
			attrLabel[i].setText("Imposta " + Utils.ability[i] + " a:");
			attrVals[i].setText("" + c.getAbils().getAsArray()[i]);
			main.add(attrLabel[i]);
			main.add(attrVals[i]);
		}
		add(new JScrollPane(main), BorderLayout.CENTER);
		
		
	}


	public void apply() {
		for (int i = 0; i < 25; ++i) {
			if (Short.parseShort(attrVals[i].getText()) != c.getAbils().getAsArray()[i])
				w.notifyChange();
			c.getAbils().setAbility(i, Short.parseShort(attrVals[i].getText()));
		}
	}
}
