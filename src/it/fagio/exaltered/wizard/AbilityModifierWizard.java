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

package it.fagio.exaltered.wizard;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import it.fagio.exaltered.model.PlayCharacter;
import it.fagio.exaltered.panels.CharModifyPanel;
import it.fagio.exaltered.utils.Utils;
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
