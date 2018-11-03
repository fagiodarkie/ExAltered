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

package it.apteroscode.exaltered.swing.saw;

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
		String[] lab = {"Numero di attacchi: ", "Penalit� massima: ", "Moti spesi: "};
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
