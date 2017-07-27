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

package it.darkfagio.metaexalted.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

public class HealthPanel extends JPanel {
	private static final long serialVersionUID = -2257965174196570998L;
	
	private MainPanel main;
	@SuppressWarnings("unused")
	private it.darkfagio.metaexalted.model.PlayCharacter c;
	private JPanel inner;
	private HealthLimbGui[] limb;

	public HealthPanel(MainPanel mainPanel, it.darkfagio.metaexalted.model.PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		limb = new HealthLimbGui[6];
		for (int i = 0; i < 6; ++i) {
			limb[i] = new HealthLimbGui(c, i, this);
		}
		remodel();
	}
	
	public void notifyChange() {
		main.notifyChange();
	}

	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
		inner = new JPanel();
		inner.setLayout(new BorderLayout());
		JPanel vitalGrid = new JPanel(), limbGrid = new JPanel();
		vitalGrid.setLayout(new GridLayout(1, 2));
		limbGrid.setLayout(new GridLayout(4,1));
		vitalGrid.add(limb[0]);
		vitalGrid.add(limb[1]);
		for (int i = 2; i < 6; ++i)
			limbGrid.add(limb[i]);
		inner.add(vitalGrid, BorderLayout.NORTH);
		add(limbGrid, BorderLayout.EAST);
		add(inner, BorderLayout.CENTER);
	}

}
