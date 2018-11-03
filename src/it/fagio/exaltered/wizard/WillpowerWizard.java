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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.fagio.exaltered.model.PlayCharacter;
import it.fagio.exaltered.model.Pools;
import it.fagio.exaltered.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class WillpowerWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	JTextField t;

	public WillpowerWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		setLayout(new BorderLayout());
		JPanel grid = new JPanel(new FlowLayout());
		grid.add(new JLabel("Modifica livelli di volont�:    "));
		t = Format.getNumericTextField("" + c.getPools().getStat(Pools.willpower));
		t.setColumns(3);
		grid.add(t);
		
		JPanel applyPanel = new JPanel(new FlowLayout());
		JButton apply = new JButton("Applica");
		applyPanel.add(apply);
		add(grid, BorderLayout.CENTER);
		add(applyPanel, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				short newVal = Short.parseShort(t.getText());
				if (newVal != c.getPools().getStat(Pools.willpower)) {
					c.getPools().setStat(Pools.willpower, newVal);
					poolPanel.notifyChange();
					poolPanel.remodel();
				}
				superDialog.dispose();
			}
		});
	}
	
	@Override
	public void setDialog(JDialog d) {
		super.setDialog(d);
		superDialog.setPreferredSize(new Dimension(400, 150));
	}

	@Override
	public String getTitle() {
		return "Modifica Volont�";
	}

}
