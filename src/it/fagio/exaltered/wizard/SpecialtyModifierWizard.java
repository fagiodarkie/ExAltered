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
import java.awt.FlowLayout;
import java.awt.GridLayout;

import it.fagio.exaltered.model.PlayCharacter;
import it.fagio.exaltered.panels.CharModifyPanel;
import it.fagio.exaltered.utils.Utils;
import it.darkfagio.utils.Format;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class SpecialtyModifierWizard extends CharModifyPanel {

	private static final long serialVersionUID = 1L;
	private JLabel firstLabel;
	private JLabel secondLabel;
	private JTextField[] abilVals, abilNames;
	private PlayCharacter c;
	private FirstStepCreationWizard w;

	public SpecialtyModifierWizard(FirstStepCreationWizard wizardDialog, final PlayCharacter c) {
		
		w = wizardDialog;
		this.c = c;
		firstLabel = new JLabel();
		abilVals = new JTextField[25];
		abilNames = new JTextField[25];
		
		setLayout(new BorderLayout());
		JPanel main = new JPanel();
		main.setLayout(new GridLayout(25, 1, 10, 10));
		
		for (int i = 0; i < 25; ++i) {
			secondLabel = new JLabel(" con bonus di ");
			firstLabel = new JLabel("La specialit� per " + Utils.ability[i] + " � ");
			abilNames[i] = new JTextField(c.getAbils().getSpecNames()[i]);
			abilNames[i].setColumns(20);
			abilVals[i] = Format.getNumericTextField("" + c.getAbils().getSpecArray()[i]);
			abilVals[i].setColumns(5);
			JPanel p1 = new JPanel();
			p1.setLayout(new FlowLayout());
			p1.add(firstLabel);
			p1.add(abilNames[i]);
			p1.add(secondLabel);
			p1.add(abilVals[i]);
			main.add(p1);
		}
		add(new JScrollPane(main), BorderLayout.CENTER);
	}
	
	public void apply() {
		for (int i = 0; i < 25; ++i) {
			if ((!abilNames[i].getText().trim().equalsIgnoreCase(c.getAbils().getSpecNames()[i]))
					|| (Short.parseShort(abilVals[i].getText()) != c.getAbils().getSpecArray()[i]))
				w.notifyChange();
			if (!abilNames[i].getText().trim().equalsIgnoreCase(""))
				c.getAbils().setSpecialization((short) i,
				abilNames[i].getText().trim(), Short.parseShort(abilVals[i].getText()));
		}
	}
}
