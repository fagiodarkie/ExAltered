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

package it.apteroscode.exaltered.simple;

import it.darkfagio.utils.Format;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIModifiableAttribute extends JPanel {
	private static final long serialVersionUID = 1L;

	public GUIModifiableAttribute(String string, String string2,
			ActionListener buttonDialogListener) {
		JButton b = new JButton(string);
		b.setPreferredSize(new Dimension(200, b.getSize().height));
		JTextField t = Format.getNumericTextField();
		t.setText(string2);
		t.setEnabled(false);
		t.setColumns(5);
		b.addActionListener(buttonDialogListener);
		setLayout(new BorderLayout());
		add(b, BorderLayout.WEST);
		add(t, BorderLayout.EAST);
	}

}
