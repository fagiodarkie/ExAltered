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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.model.pool.Pools;
import it.apteroscode.exaltered.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class PeripheralEssenceWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private JTextField t;
	private JTextField u;

	public PeripheralEssenceWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(new FlowLayout()), grid = new JPanel(new GridLayout(2, 1, 0, 10)),
				applyPanel = new JPanel(new FlowLayout());
		add(inner, BorderLayout.CENTER);
		grid.add(new JLabel("Modifica bonus all'essenza Periferica:"));
		t = Format.getNumericTextField("" + c.getPools().getStat(Pools.peripheralEssenceBonus));
		grid.add(t);
		grid.add(new JLabel("Spendi essenza..."));
		u = Format.getNumericTextField();
		u.setColumns(3);
		grid.add(u);
		
		JButton apply = new JButton("Applica");
		applyPanel.add(apply);
		add(grid, BorderLayout.CENTER);
		add(applyPanel, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				short newVal = Short.parseShort(t.getText());
				boolean mod = false;
				if (newVal != c.getPools().getStat(Pools.peripheralEssenceBonus)) {
					mod = true;
					c.getPools().setStat(Pools.peripheralEssenceBonus, newVal);
				}
				if (!u.getText().trim().equalsIgnoreCase("") || !u.getText().trim().equalsIgnoreCase("0")) {
					mod = true;
					c.getPools().setStat(Pools.usedPeripheralEssence,
							(short) (c.getPools().getStat(Pools.usedPeripheralEssence) + Short.parseShort(u.getText().trim())));
				}
				if (mod) {
					poolPanel.notifyChange();
					poolPanel.remodel();
				}
				superDialog.dispose();
			}
		});		
	}

	@Override
	public String getTitle() {
		return "Modifica l'essenza Periferica";
	}

}
