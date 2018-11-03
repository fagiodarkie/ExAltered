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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.apteroscode.exaltered.model.TempEffect;
import it.apteroscode.exaltered.panels.EffectsModifier;
import it.apteroscode.exaltered.panels.EquipmentPanel;
import it.fagio.exaltered.model.PlayCharacter;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class AddItemWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private EquipmentPanel eq;
	private PlayCharacter c;
	private Item w;
	private JTextArea description;
	private JTextField[] fieldGrid;
	private EffectsModifier effs, attEffs;
	private JTextField nameField, attuneCost;
	private int oldWeaponPosition;
	private boolean mod;
	
	
	// Nota: la durabilit� corrente non � modificabile in fase di creazione.
	
	public AddItemWizard(EquipmentPanel ePanel, PlayCharacter ch, boolean modify) {
		c = ch;
		eq = ePanel;
		mod = modify;
		if (!mod) {
			oldWeaponPosition = -1;
			w = new Item();
			effs = new EffectsModifier();
			attEffs = new EffectsModifier();
		} else {
			oldWeaponPosition = ePanel.itemList.getSelectedIndex();
			w = c.equip.item.get(oldWeaponPosition);
			effs = new EffectsModifier(w.getEffects());
			attEffs = new EffectsModifier(w.getAttunedEffects());
		}
		fieldGrid = new JTextField[2];
		remodel();
		effs.setSource(TempEffect.T_EQUIP);
		attEffs.setSource(TempEffect.T_EQUIP);
	}
	
	public void remodel() {
		removeAll();
		
		setLayout(new BorderLayout());
		
		JPanel head = new JPanel(new GridLayout(4, 2, 5, 5));
		head.add(new JLabel("Nome:"));
		nameField = new JTextField(w.getName());
		head.add(nameField);
		head.add(new JLabel("Costo Armonizzazione:"));
		attuneCost = Format.getNumericTextField("" + w.getEssence());
		head.add(attuneCost);
		fieldGrid[0] = Format.getNumericTextField("" + w.getWeightArray()[0]);
		fieldGrid[1] = Format.getNumericTextField("" + w.getWeightArray()[1]);
		head.add(new JLabel("Peso:"));
		head.add(fieldGrid[0]);
		head.add(new JLabel("Peso da armonizzato:"));
		head.add(fieldGrid[1]);
		
		add(head, BorderLayout.NORTH);
		
		JPanel effects = new JPanel(new BorderLayout());
		effs.setBorder(BorderFactory.createTitledBorder("Effetti"));
		attEffs.setBorder(BorderFactory.createTitledBorder("Effetti da armonizzato"));
		effects.add(effs, BorderLayout.NORTH);
		effects.add(attEffs, BorderLayout.SOUTH);
		JScrollPane effectsPane = new JScrollPane(effects);

		add(effectsPane, BorderLayout.CENTER);
		
		description = new JTextArea(w.getDescription());
		description.setRows(3);
		JPanel descPanel = new JPanel(new GridLayout(1, 1));
		descPanel.add(description);
		descPanel.setBorder(BorderFactory.createTitledBorder("Descrizione"));
		JPanel footer = new JPanel(new BorderLayout());
		footer.add(descPanel, BorderLayout.CENTER);
		JPanel appRefr = new JPanel(new GridLayout(1, 2, 5, 0));
		JButton apply = new JButton("Applica"), refresh = new JButton("Ricarica");
		appRefr.add(apply);
		appRefr.add(refresh);
		refresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				backupInfo();
				remodel();
			}
		});
		footer.add(appRefr, BorderLayout.SOUTH);
		add(footer, BorderLayout.SOUTH);
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				backupInfo();
				if (oldWeaponPosition >= 0) {
					c.equip.item.remove(oldWeaponPosition);
				}
				c.equip.addItem(w);
				eq.notifyChange();
				superDialog.dispose();
			}
		});
		
		invalidate();
		validate();
	}
		
	private void backupInfo() {
		w = new Item();
		float weight1 = Float.parseFloat(fieldGrid[0].getText().trim());
		float weight2 = (fieldGrid[1].getText().trim().equalsIgnoreCase("") ? weight1 :
					Float.parseFloat(fieldGrid[1].getText().trim()));
		float[] weight = {weight1, weight2};
		w.setWeight(weight);
		
		w.setName(nameField.getText().trim());
		w.setDescription(description.getText().trim());
		w.setEssenceCost(Short.parseShort(attuneCost.getText().trim()));
		w.setEffects(effs.getEffects());
		w.setAttunedEffects(attEffs.getEffects());
	}

	@Override
	public String getTitle() {
		return (!mod ? "Nuovo Oggetto" : "Modifica Oggetto");
	}
}
