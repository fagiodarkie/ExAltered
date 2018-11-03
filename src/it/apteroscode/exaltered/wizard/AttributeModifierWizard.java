/*
Author: Jacopo Freddi
Project: ExAltered
Description: Interactive Character Sheet for a heavily-altered version of
  the  role-playing game Exalted, 2nd Edition, by White Wolf Publishing.

Copyright (C) 2014 - 2017 Jacopo Freddi

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

� 2017 White Wolf Entertainment AB. All rights reserved.
Exalted� and Storytelling System� are trademarks and/or
registered trademarks of White Wolf Entertainment AB.

All rights reserved. www.white-wolf.com
*/

package it.apteroscode.exaltered.wizard;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.panels.CharModifyPanel;
import it.apteroscode.exaltered.utils.Utils;
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
