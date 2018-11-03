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

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import it.apteroscode.exaltered.model.pool.HealthManager;
import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.panels.HealthLimbGui;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class ModifyHealthWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private PlayCharacter c;
	private int position;
	private HealthLimbGui h;
	private boolean m;
	
	//TODO reformulate to simplify procedure
	
	public ModifyHealthWizard(PlayCharacter ch, int pos, HealthLimbGui healthLimbGui) {
		c = ch;
		position = pos;
		m = false;
		h = healthLimbGui;
		setLayout(new BorderLayout());
		
		JPanel apply = new JPanel();
		apply.setLayout(new FlowLayout());
		JButton applyButton = new JButton("Applica");
		JButton finish = new JButton("Fine");
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				superDialog.dispose();
			}
		});
		apply.add(applyButton);
		apply.add(finish);
		
		final JPanel radios = new JPanel(), level = new JPanel(), toBlock = new JPanel(), block = new JPanel();
		radios.setLayout(new GridLayout(3,1));
		level.setLayout(new FlowLayout());
		toBlock.setLayout(new FlowLayout());
		block.setLayout(new FlowLayout());
		final JTextField levNumber = Format.getNumericTextField("" + ch.getHealth().get(position).getTotalHealthLevels()[0]);
		levNumber.setColumns(3);
		final JComboBox<String> levType = new JComboBox<String>();
		for(int i = 0; i < c.getHealth().get(position).getHealthLevelNumber(); ++i) {
			short p = c.getHealth().get(position).getPenaltyLevels()[i];
			if (p == Short.MIN_VALUE) levType.addItem("INC");
			else levType.addItem("" + p);
		}
		levType.setSelectedIndex(0);
		
		level.add(levNumber);
		level.add(new JLabel(" livelli "));
		
		toBlock.add(new JLabel(" al blocco "));
		block.add(levType);
		
		ButtonGroup group = new ButtonGroup();
		final JRadioButton assign = new JRadioButton("Assegna"), add = new JRadioButton("Aggiungi"), remove = new JRadioButton("Rimuovi");
		assign.setSelected(true);
		group.add(assign);
		group.add(add);
		group.add(remove);
		radios.add(assign);
		radios.add(add);
		radios.add(remove);
		
		levType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (assign.isSelected()) levNumber.setText(""
						+ c.getHealth().get(position).getTotalHealthLevels()[levType.getSelectedIndex()]);
			}
		});
		applyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				short levIndex = (short) levType.getSelectedIndex();
				short oldAmount = c.getHealth().get(position).getTotalHealthLevels()[levIndex];
				short amount = Short.parseShort(levNumber.getText().trim());

				if (assign.isSelected()) {
					if (!m && (amount != oldAmount)) {
						m = true;
						h.notifyChange();
					}
					c.getHealth().get(position).setTotalHealthLevels(levIndex, amount);
				} else if (add.isSelected()) {
					if (!m && (amount != 0)) {
						m = true;
						h.notifyChange();
					}
					c.getHealth().get(position).addHealthLevels(levIndex, amount);
				} else if (remove.isSelected()) {
					if (!m && (amount != oldAmount)) {
						m = true;
						h.notifyChange();
					}
					c.getHealth().get(position).removeHealthLevels(levIndex, amount);
				}
				h.remodel();
			}
		});
		
		JPanel center = new JPanel();
		center.setLayout(new GridLayout(2, 2));
		center.add(radios);
		center.add(level);
		center.add(toBlock);
		center.add(block);
		add(center, BorderLayout.CENTER);
		add(apply, BorderLayout.SOUTH);
		
	}
	
	@Override
	public String getTitle() {
		return "Modifica i livelli " + HealthManager.NAME[position];
	}

}
