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

package it.apteroscode.exaltered.swing.wizard;

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

import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.core.model.pool.Virtues;
import it.apteroscode.exaltered.swing.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class VirtueModifierWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PoolPanel a;
	private PlayCharacter ch;

	public VirtueModifierWizard(PoolPanel poolPanel, PlayCharacter c) {
		a = poolPanel;
		ch = c;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(2, 2, 10, 10));
		
		inner.add(new JLabel("Modifica Virt�: "));
		
		final JComboBox<String> attributes = new JComboBox<String>(Virtues.name);

		inner.add(attributes);
		
		inner.add(new JLabel("Nuovo valore: "));
		final JTextField text = Format.getNumericTextField("" + c.getPools().getVirtues().getStat(Virtues.compassion));
		text.setColumns(3);
		inner.add(text);
		outer.add(inner);
		add(outer, BorderLayout.CENTER);
		attributes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText("" + ch.getPools().getVirtues().getStat((short) attributes.getSelectedIndex()));
			}
		});
		
		JPanel butts = new JPanel();
		butts.setLayout(new FlowLayout());
		JButton button = new JButton("Applica");
		butts.add(button);
		add(butts, BorderLayout.SOUTH);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ch.getPools().getVirtues().setStat((short) attributes.getSelectedIndex(), Integer.parseInt(text.getText().trim()));
				a.notifyChange();
				superDialog.dispose();
			}
		});
		
	}
	
	@Override
	public String getTitle() {

		return "Modifica una Virt�";
	}


}
