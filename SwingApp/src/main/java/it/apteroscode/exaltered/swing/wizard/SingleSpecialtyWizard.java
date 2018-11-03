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

import it.fagio.exaltered.model.Abilities;
import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.swing.panels.AttributesPanel;
import it.apteroscode.exaltered.utils.Utils;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

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

public class SingleSpecialtyWizard extends DialogPanel {

	private static final long serialVersionUID = 1L;
	private AttributesPanel a;
	public SingleSpecialtyWizard(AttributesPanel attributesPanel, final PlayCharacter c) {
		a = attributesPanel;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(3, 2, 10, 10));
		
		inner.add(new JLabel("Nome della specializzazione: "));		
		final JTextField spec = new JTextField(30);
		inner.add(spec);
		
		inner.add(new JLabel("Abilit�:"));
		final JComboBox<String> specAb = new JComboBox<String>();
		for (int i = 0; i < Utils.ability.length; ++i)
			specAb.addItem(Utils.ability[i]);
		specAb.setSelectedIndex(Abilities.CRAFT);
		inner.add(specAb);
		
		inner.add(new JLabel("Valore: "));
		final JTextField specVal = Format.getNumericTextField();
		specVal.setColumns(3);
		inner.add(specVal);
		outer.add(inner);
		add(outer, BorderLayout.CENTER);

		
		JPanel butts = new JPanel();
		butts.setLayout(new FlowLayout());
		JButton button = new JButton("Applica");
		butts.add(button);
		add(butts, BorderLayout.SOUTH);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!(spec.getText().trim().equalsIgnoreCase("") || specVal.getText().trim().equalsIgnoreCase("") )) {
					c.getAbils().addSpec(spec.getText().trim(),
							(short) specAb.getSelectedIndex(),
							(Short.parseShort(specVal.getText())));
					a.notifyChange();
				}
				superDialog.dispose();
			}
		});
	}
	
	@Override
	public String getTitle() {
		return "Aggiungi Specializzazione";
	}


}
