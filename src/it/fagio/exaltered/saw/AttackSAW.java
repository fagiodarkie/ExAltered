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

package it.fagio.exaltered.saw;

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
