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

package it.fagio.exaltered.simple;

import it.darkfagio.utils.Format;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUIAttribute extends JPanel {

	private static final long serialVersionUID = 1L;

	protected String name;
	private JLabel value;

	
	public GUIAttribute(String n, int v, String specName, short specVal) {
		name = n + (specVal > 0 ? (" (" + specName + ": +" + specVal + ")") : "");
		value = new JLabel();
		String s = "";
		for (int i = 0; i < v; ++i) {
			s += "\u25CF";
		}
		value.setText("" + s);
		setLayout(new BorderLayout());
		add(new JLabel(name), BorderLayout.WEST);
		add(value, BorderLayout.EAST);
	}

	public GUIAttribute(String n, String s) {
		name = n;
		value = new JLabel(s);
		setLayout(new BorderLayout());
		add(new JLabel(name), BorderLayout.WEST);
		add(value, BorderLayout.EAST);
	}

	public GUIAttribute(String string, int v) {
		this(string, "");
		String s = "";
		for (int i = 0; i < v; ++i) {
			s += "\u25CF";
		}
		value.setText("" + s);
	}

	public GUIAttribute(String string, double k) {
		this(string, Format.formatDecimal((float) k));
	}

	public GUIAttribute(String n, String showAbility, String specName,
			short specVal) {
		this(n + (specVal > 0 ? (" (" + specName + ": +" + specVal + ")") : ""), showAbility);
	}

	public void setValue(int v) {
		value.setText("" + v);
	}
	
	public int getValue() {
		return Integer.parseInt(value.getText());
	}
	
}
