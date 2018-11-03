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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.apteroscode.exaltered.model.pool.HealthManager;
import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.panels.HealthLimbGui;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class DamageWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;

	private HealthLimbGui gui;
	private PlayCharacter ch;
	private int position;

	public DamageWizard(PlayCharacter c, int pos, HealthLimbGui healthLimbGui) {
		gui = healthLimbGui;
		this.ch = c;
		this.position = pos;
		setLayout(new BorderLayout());

		JButton ok = new JButton("Applica");
		JLabel label1 = new JLabel("Seleziona il tipo di danno:");
		JLabel label2 = new JLabel("Quanti livelli infliggere?");
		
		JPanel grid = new JPanel(), row1 = new JPanel(), row2 = new JPanel();
		row1.setLayout(new BorderLayout());
		row2.setLayout(new BorderLayout());
		grid.setLayout(new GridLayout(2, 1));
		final JTextField levelNumber = Format.getNumericTextField();
		levelNumber.setColumns(3);
		final JComboBox<String> levelType = new JComboBox<String>();
		for (int i = 0; i < 3; ++i)
			levelType.addItem(HealthManager.DAMAGE[i]);
		
		ok.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String t = levelNumber.getText().trim();
				short healed = ((t != null) && !t.equalsIgnoreCase("") ? Short.parseShort(t) : 0);
				int levelToHeal = levelType.getSelectedIndex();
				if (healed > 0) {
					ch.getHealth().get(position).addDamage(healed, (short)levelToHeal);
					gui.notifyChange();
				}
				superDialog.dispose();
			}
		});
		row1.add(label1, BorderLayout.WEST);
		row1.add(levelType, BorderLayout.CENTER);
		row2.add(label2, BorderLayout.WEST);
		row2.add(levelNumber, BorderLayout.CENTER);
		grid.add(row1);
		grid.add(row2);
		JPanel flow = new JPanel();
		flow.setLayout(new FlowLayout());
		flow.add(grid);
		JPanel flow2 = new JPanel();
		flow2.setLayout(new FlowLayout());
		flow2.add(ok);
		
		add(flow, BorderLayout.CENTER);
		add(flow2, BorderLayout.SOUTH);
	}
	
	@Override
	public String getTitle() {
		return "Danneggia " + HealthManager.NAME[gui.getPosition()];
	}

}
