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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.darkfagio.metaexalted.model.Minimum;
import it.darkfagio.utils.gui.DialogPanel;

public class MinimumViewer extends DialogPanel {
	private static final long serialVersionUID = 1L;
	
	private MinimumManager manag;
	private JComboBox<String> trait;
	private JTextField value;

	private int oldIndex;

	public MinimumViewer(MinimumManager minimumManager) {
		manag = minimumManager;
		oldIndex = -1;
		trait = new JComboBox<String>(Minimum.getStatNames());
		value = new JTextField(3);
		
		remodel();
	}

	public void remodel() {
		
		removeAll();
		
		setLayout(new BorderLayout());
		JPanel center = new JPanel(new FlowLayout()),
				button = new JPanel(new FlowLayout()),
				centerGrid = new JPanel(new GridLayout(2, 2));
		centerGrid.add(new JLabel("Attributo: "));
		centerGrid.add(trait);
		centerGrid.add(new JLabel("Valore minimo: "));
		centerGrid.add(value);
		center.add(centerGrid);
		add(center, BorderLayout.CENTER);
		
		JButton apply = new JButton("Applica");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backup();
				superDialog.dispose();
			}
		});
		button.add(apply);
		add(button, BorderLayout.SOUTH);
		
		invalidate();
		validate();
	}
	
	public void backup() {
		Minimum m = getMin();
		if (m == null) return;
		if (oldIndex > 0) manag.setMinimum(oldIndex, m);
		else manag.addMinimum(m);
	}	
	
	private Minimum getMin() {
		short val = Short.parseShort(value.getText());
		if (val <= 0) return null;
		return new Minimum((short) Minimum.convertListIndexToStat(trait.getSelectedIndex()), val);
	}

	public void setMin(Minimum minimum, int i) {
		oldIndex = i;
		trait.setSelectedIndex(Minimum.convertStatToListIndex(minimum.getStat()));
		value.setText("" + minimum.getValue());
		remodel();
	}

	@Override
	public String getTitle() {
		return "Crea / Modifica Minimo";
	}
}
