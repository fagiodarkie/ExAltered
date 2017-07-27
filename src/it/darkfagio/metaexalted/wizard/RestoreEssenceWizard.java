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

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.panels.PoolPanel;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class RestoreEssenceWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	
	public RestoreEssenceWizard(final PoolPanel poolPanel, final PlayCharacter c) {
		
		setLayout(new BorderLayout());
		JPanel center = new JPanel(new FlowLayout()),
				buttons = new JPanel(new FlowLayout()),
				item = new JPanel(new GridLayout(1, 3));
		final JTextField value = Format.getNumericTextField("0");
		value.setColumns(3);
		JButton apply = new JButton("Applica");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				c.pools.restoreEssence(Short.parseShort(value.getText()));
				poolPanel.notifyChange();
				superDialog.dispose();
				
			}
		});
		item.add(new JLabel("Ripristina "));
		item.add(value);
		item.add(new JLabel(" moti"));
		add(center, BorderLayout.CENTER);
		center.add(item);
		buttons.add(apply);
		add(buttons, BorderLayout.SOUTH);
	}

	@Override
	public String getTitle() {
		return "Ripristina Moti";
	}
}
