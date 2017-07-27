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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import it.darkfagio.metaexalted.model.TempEffect;
import it.darkfagio.metaexalted.utils.Utils;
import it.darkfagio.utils.gui.DialogPanel;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("unused")
public class EffectViewer extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private JTextField name, value;
	private JComboBox<String> stat, type;
	private int source;
	private JCheckBox active;
	private EffectsModifier em;
	
	public EffectViewer(EffectsModifier e, TempEffect effect) {
		name = new JTextField(effect.getDescription());
		em = e;
		stat = new JComboBox<String>(TempEffect.getBonusNameList());
		int index = effect.getPosition();
		index = normalizeIn(index);
		stat.setSelectedIndex(index);

		active = new JCheckBox("Attivo");
		active.setSelected(effect.isActive());
		value = new JTextField("" + effect.getValue());
		source = effect.getType();

		remodel();
	}
	
	public EffectViewer(EffectsModifier e) {
		name = new JTextField();
		em = e;
		stat = new JComboBox<String>(TempEffect.getBonusNameList());
		value = new JTextField("0");
		active = new JCheckBox("Attivo");
		type = new JComboBox<String>();
		for (int i = 0; i < TempEffect.typeName.length; ++i)
			type.addItem(TempEffect.typeName[i]);
		type.setSelectedIndex(0);
		source = -1;

		
		remodel();
	}
	
	public EffectViewer(EffectsModifier e, int t) {
		source = t;
		em = e;
		stat = new JComboBox<String>(TempEffect.getBonusNameList());
		name = new JTextField("Nome qui");
		value = new JTextField("0");
		active = new JCheckBox("Attivo");
		
		remodel();
	}
	
	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
		JPanel upper = new JPanel(new BorderLayout()),
				center = new JPanel(new BorderLayout()),
				lower = new JPanel(new BorderLayout()),
				button = new JPanel(new FlowLayout());
		
		JButton apply = new JButton("Applica");
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				em.sendTempEffect(getEffect());
				superDialog.dispose();
			}
		});
		button.add(apply);
		upper.add(name, BorderLayout.WEST);
		upper.add(stat, BorderLayout.CENTER);
		upper.add(value, BorderLayout.EAST);
		name.setColumns(10);
		value.setColumns(3);
		if (source < 0) center.add(type, BorderLayout.EAST);
		else center.add(new JLabel(TempEffect.typeName[source]), BorderLayout.EAST);
		center.add(active, BorderLayout.WEST);
		add(upper, BorderLayout.NORTH);
		lower.add(center, BorderLayout.NORTH);
		lower.add(button, BorderLayout.SOUTH);
		add(lower, BorderLayout.SOUTH);
		
		invalidate();
		validate();
	}
	
	public TempEffect getEffect() {
		if (!(name.getText().trim().equalsIgnoreCase("") || value.getText().trim().equalsIgnoreCase(""))) {
			short index = (short) stat.getSelectedIndex();
			TempEffect eff = new TempEffect(name.getText().trim(),
					(short) normalizeOut(index),
					Short.parseShort(value.getText().trim()),
					active.isSelected(),
					(short) (source < 0 ? type.getSelectedIndex() : source));
			if (eff.getValue() == 0) return null;
			return eff;
		}
		return null;
	}

	public void setEffect(TempEffect effect) {
		name = new JTextField(effect.getDescription());
		int index = effect.getPosition();
		index = normalizeIn(index);
		stat.setSelectedIndex(index);
		active.setSelected(effect.isActive());
		value.setText("" + effect.getValue());
		source = effect.getType();

		remodel();
	}

	private int normalizeIn(int index) {
		// 0 - 8 attribute;
		if (index > 8) index --;
		// 9 - 33 ability;
		if (index > 33) index -= 5;
		// 34 - 41 pools.
		if (index > 41) index -= 13;
		// 42 - 47 others
		// 60 + etc
		return index;
	}
	private int normalizeOut(int index) {
		if (index > 8) index ++;
		if (index > 34) index += 5;
		if (index > 47) index += 13;
		return index;
	}

	public void setSource(int src) {
		source = src;
		remodel();
	}

	@Override
	public String getTitle() {
		return "Crea / Modifica Effetto";
	}
}
