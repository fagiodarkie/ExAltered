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

package it.apteroscode.exaltered.saw;

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
