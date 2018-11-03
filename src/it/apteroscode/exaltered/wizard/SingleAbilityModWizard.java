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

import it.apteroscode.exaltered.panels.AttributesPanel;
import it.apteroscode.exaltered.utils.Utils;
import it.fagio.exaltered.model.Abilities;
import it.fagio.exaltered.model.PlayCharacter;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class SingleAbilityModWizard extends DialogPanel {

	private static final long serialVersionUID = 5735383292129899153L;
	private AttributesPanel a;

	public SingleAbilityModWizard(AttributesPanel attributesPanel, final PlayCharacter c) {
		a = attributesPanel;
		setLayout(new BorderLayout());
		JPanel inner = new JPanel(), outer = new JPanel();
		outer.setLayout(new FlowLayout());
		inner.setLayout(new GridLayout(4, 2, 10, 10));
		
		inner.add(new JLabel("Modifica abilit�: "));
		
		final JComboBox<String> attributes = new JComboBox<String>();
		for (int i = 0; i < 25; ++i) {
			attributes.addItem(Utils.ability[i]);
		}
		inner.add(attributes);
		
		inner.add(new JLabel("Nuovo valore: "));
		final JTextField text = Format.getNumericTextField();
		text.setColumns(3);
		text.setText("" + c.getAbils().getAsArray()[Abilities.ARCHERY]);
		inner.add(text);
		add(inner, BorderLayout.CENTER);

		inner.add(new JLabel("Specializzazione (se presente): "));		
		final JTextField spec = new JTextField(30);
		spec.setText(c.getAbils().getSpecNames()[0]);
		inner.add(spec);
		
		inner.add(new JLabel("Valore: "));
		final JTextField specVal = Format.getNumericTextField();
		specVal.setColumns(3);
		specVal.setText("" + c.getAbils().getSpecArray()[0]);
		inner.add(specVal);
		
		outer.add(inner);
		add(outer, BorderLayout.CENTER);
		
		attributes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				text.setText("" + c.getAbils().getAsArray()[attributes.getSelectedIndex()]);
				spec.setText(c.getAbils().getSpecNames()[attributes.getSelectedIndex()]);
				specVal.setText("" + c.getAbils().getSpecArray()[attributes.getSelectedIndex()]);
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
				short ab = Short.parseShort(text.getText().equalsIgnoreCase("") ? "0" : text.getText());
				short sp = Short.parseShort(specVal.getText().equalsIgnoreCase("") ? "0" : specVal.getText());
				String spn = spec.getText().trim();
				if (c.getAbils().getAsArray()[attributes.getSelectedIndex()] != ab
					|| c.getAbils().getSpecArray()[attributes.getSelectedIndex()] != sp
					|| !c.getAbils().getSpecNames()[attributes.getSelectedIndex()].equalsIgnoreCase(spn)) {
					a.notifyChange();
					c.getAbils().setAbility(attributes.getSelectedIndex(), ab);
					if (!spec.getText().trim().equalsIgnoreCase(""))
						c.getAbils().setSpecialization((short) attributes.getSelectedIndex(),
							spec.getText().trim(), (Short.parseShort(specVal.getText())));
				}
				superDialog.dispose();
			}
		});

	}
	
	@Override
	public String getTitle() {
		return "Modifica un'Abilit�";
	}

}
