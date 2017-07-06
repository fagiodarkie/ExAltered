package it.darkfagio.metaexalted.wizard;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.CharModifyPanel;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.Format;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AttributeModifierWizard extends CharModifyPanel {
	private static final long serialVersionUID = 1L;
	private JLabel[] attrLabel;
	private JTextField[] attrVals;
	private PlayCharacter c;
	private FirstStepCreationWizard w;

	public AttributeModifierWizard(FirstStepCreationWizard wizardDialog, final PlayCharacter c) {
		w = wizardDialog;
		this.c = c;
		attrLabel = new JLabel[9];
		attrVals = new JTextField[9];
		
		setLayout(new BorderLayout());
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(9, 2, 10, 10));
		
		for (int i = 0; i < 9; ++i) {
			attrLabel[i] = new JLabel();
			attrVals[i] = Format.getNumericTextField();
			attrLabel[i].setText("Imposta " + Utils.attribute[i] + " a:");
			attrVals[i].setText("" + c.getAtts().getAsArray()[i]);
			main.add(attrLabel[i]);
			main.add(attrVals[i]);
		}
		add(main, BorderLayout.CENTER);
		
	}

	@Override
	public void apply() {
		for (int i = 0; i < 9; ++i) {
			if (c.getAtts().getAsArray()[i] != Short.parseShort(attrVals[i].getText())) {
				w.notifyChange();
				c.getAtts().setAttribute(i, Short.parseShort(attrVals[i].getText()));
			}
		}
	}

}
