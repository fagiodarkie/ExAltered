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

package it.apteroscode.exaltered.swing.wizard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import it.apteroscode.exaltered.core.model.equipment.Armor;
import it.apteroscode.exaltered.core.model.pool.HealthManager;
import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.core.model.TempEffect;
import it.apteroscode.exaltered.swing.panels.DamageBonusVisualizer;
import it.apteroscode.exaltered.swing.panels.EffectsModifier;
import it.apteroscode.exaltered.swing.panels.EquipmentPanel;
import it.apteroscode.exaltered.utils.Utils;
import it.darkfagio.utils.Format;
import it.darkfagio.utils.gui.DialogPanel;

public class AddArmorWizard extends DialogPanel {
	private static final long serialVersionUID = 1L;
	private EquipmentPanel eq;
	private PlayCharacter c;
	private Armor w;
	private JTextArea description;
	private JTextField nameField, attuneCost;
	private JTextField[] fieldGrid;
	private EffectsModifier effs, attEffs;
	private int oldWeaponPosition;
	private DamageBonusVisualizer normalSoak, attunedSoak;
	private boolean mod;
	private JCheckBox[] bodyParts;
	
	
	// Nota: la durabilit� corrente non � modificabile in fase di creazione.
	
	public AddArmorWizard(EquipmentPanel ePanel, PlayCharacter ch, boolean modify) {
		c = ch;
		eq = ePanel;
		mod = modify;
		bodyParts = new JCheckBox[6];
		for (int i = 0; i < 6; ++i) {
			bodyParts[i] = new JCheckBox(HealthManager.NAME[i]);
		}
		if (!mod) {
			oldWeaponPosition = -1;
			w = new Armor();
			effs = new EffectsModifier();
			attEffs = new EffectsModifier();
		} else {
			oldWeaponPosition = ePanel.armorList.getSelectedIndex();
			w = c.equip.armor.get(oldWeaponPosition);
			effs = new EffectsModifier(w.getEffects());
			attEffs = new EffectsModifier(w.getAttunedEffects());
			short[] activeParts = w.getBodyParts();
			for (int i = 0; i < activeParts.length; ++i)
				bodyParts[activeParts[i]].setSelected(true);
		}
		effs.setSource(TempEffect.T_EQUIP);
		attEffs.setSource(TempEffect.T_EQUIP);
		remodel();
	}
	
	public void remodel() {
		removeAll();
		setLayout(new BorderLayout());
		
		JPanel head = new JPanel(new GridLayout(2, 2, 5, 5));
		head.add(new JLabel("Nome:"));
		nameField = new JTextField(w.getName());
		head.add(nameField);
		head.add(new JLabel("Costo Armonizzazione:"));
		attuneCost = Format.getNumericTextField(); 
		attuneCost.setText("" + w.getEssence());
		head.add(attuneCost);
		
		add(head, BorderLayout.NORTH);
		
		JPanel statGrid = new JPanel(new GridLayout(6, 3, 8, 5));
		statGrid.add(new JPanel());
		statGrid.add(new JLabel("Non armonizzato:"));
		statGrid.add(new JLabel("Armonizzato:"));
		fieldGrid = new JTextField[10];
		for(int i = 0; i < 5; ++i) {
			statGrid.add(new JLabel(Utils.armorStat[i]));
			fieldGrid[i] = Format.getNumericTextField((i == 0 ? "" + w.getWeightArray()[0] : "" + w.getStat((short) (i - 1))));
			fieldGrid[i + 5] = Format.getNumericTextField((i == 0 ? "" + w.getWeightArray()[1] : "" + w.getArmonizedStats(i - 1)));
			statGrid.add(fieldGrid[i]);
			statGrid.add(fieldGrid[i + 5]);
			
		}
		JPanel effects = new JPanel(new BorderLayout());
		effs.setBorder(BorderFactory.createTitledBorder("Effetti"));
		attEffs.setBorder(BorderFactory.createTitledBorder("Effetti da Armonizzato"));
		
		normalSoak = new DamageBonusVisualizer(w.getNormalSoak());
		attunedSoak = new DamageBonusVisualizer(w.getAttunedSoak());
		normalSoak.setTitle("Normale");
		attunedSoak.setTitle("Armonizzato");
		
		JPanel soaks = new JPanel(new GridLayout(3, 1));
		
		soaks.add(DamageBonusVisualizer.getHeader());
		soaks.add(normalSoak);
		soaks.add(attunedSoak);
		soaks.setBorder(BorderFactory.createTitledBorder("Assorbimento"));
		effects.add(effs, BorderLayout.NORTH);
		effects.add(attEffs, BorderLayout.SOUTH);
		JPanel footEffects = new JPanel(new BorderLayout());
		footEffects.add(soaks, BorderLayout.NORTH);
		footEffects.add(effects, BorderLayout.CENTER);

		JPanel bodyPart = new JPanel(new GridLayout(3, 2, 5, 5));
		bodyPart.setBorder(BorderFactory.createTitledBorder("Parti del corpo coperte"));
		for (int i = 0; i < 6; ++i)
			bodyPart.add(bodyParts[i]);
		
		JPanel center = new JPanel(new BorderLayout());
		center.add(bodyPart, BorderLayout.NORTH);
		center.add(statGrid, BorderLayout.CENTER);
		center.add(footEffects, BorderLayout.SOUTH);
		JScrollPane centerScroll = new JScrollPane(center);
		add(centerScroll, BorderLayout.CENTER);	
		
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
					c.equip.armor.remove(oldWeaponPosition);
				}
				c.equip.addArmor(w);
				eq.notifyChange();
				superDialog.dispose();
			}
		});
		
		invalidate();
		validate();
	}
		
	private void backupInfo() {
		w = new Armor();
		float weight1 = Float.parseFloat(fieldGrid[0].getText().trim());
		float weight2 = (fieldGrid[5].getText().trim().equalsIgnoreCase("") ? weight1 :
					Float.parseFloat(fieldGrid[5].getText().trim()));
		float[] weight = {weight1, weight2};
		w.setWeight(weight);
		
		w.setName(nameField.getText().trim());
		w.setDescription(description.getText().trim());
		w.setEssenceCost(Short.parseShort(attuneCost.getText().trim()));
		w.setEffects(effs.getEffects());
		w.setAttunedEffects(attEffs.getEffects());
		
		short[] stats = new short[4], attStats = new short[4];
		
		for (int i = 1; i < 5;++i) {
			stats[i - 1] = Short.parseShort(fieldGrid[i].getText().trim());
			short eventually = Short.parseShort(fieldGrid[i + 5].getText().trim());
			attStats[i - 1] = eventually;
		}
		w.setBodyPart(new short[0]);
		for (int i = 0; i < 6; ++i) {
			if (bodyParts[i].isSelected()) w.addBodyPart((short) i);
		}
		w.setNormalStats(stats);
		w.setArmonizedStats(attStats);
		w.setNormalSoak(normalSoak.getBonus());
		w.setAttunedSoak(attunedSoak.getBonus());
	}

	@Override
	public String getTitle() {
		return (!mod ? "Nuovo Oggetto" : "Modifica Oggetto");

	}
}
