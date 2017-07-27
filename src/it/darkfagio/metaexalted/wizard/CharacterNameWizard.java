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

© 2017 White Wolf Entertainment AB. All rights reserved.
Exalted® and Storytelling System™ are trademarks and/or
registered trademarks of White Wolf Entertainment AB.

All rights reserved. www.white-wolf.com
*/

package it.darkfagio.metaexalted.wizard;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.MainPanel;
import it.darkfagio.utils.gui.DialogPanel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CharacterNameWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter ch;
	private MainPanel mp;
	private JTextField name;
	
	public CharacterNameWizard(MainPanel m, PlayCharacter c) {
		this.ch = c;
		this.mp = m;
		setLayout(new BorderLayout());
		name = new JTextField((c.getName() == null || c.getName().equalsIgnoreCase("")) ? "" : c.getName());
		name.setColumns(30);
		JLabel lab = new JLabel("Nuovo nome: ");
		JPanel cont = new JPanel();
		cont.setLayout(new FlowLayout());
		cont.add(lab);
		cont.add(name);
		add(cont, BorderLayout.CENTER);
		JButton apply = new JButton("Applica");
		add(apply, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!name.getText().trim().equalsIgnoreCase(ch.getName())) {
					mp.notifyChange();
					ch.setName(name.getText().trim());
				}
				superDialog.dispose();
			}
		});

	}
	
	@Override
	public String getTitle() {
		return "Cambia nome...";
	}

}
