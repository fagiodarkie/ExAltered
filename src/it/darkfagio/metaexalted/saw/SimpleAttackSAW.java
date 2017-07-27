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

package it.darkfagio.metaexalted.saw;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import it.darkfagio.metaexalted.model.Abilities;
import it.darkfagio.metaexalted.model.HealthManager;
import it.darkfagio.metaexalted.model.Movement;
import it.darkfagio.metaexalted.model.PlayCharacter;
import it.darkfagio.metaexalted.model.Virtues;
import it.darkfagio.metaexalted.model.Weapon;
import it.darkfagio.metaexalted.panels.CombatStatsPanel;
import it.darkfagio.utils.Format;

public class SimpleAttackSAW extends SAWPanel {
	private static final long serialVersionUID = 1L;
	private SolveAttackWizard saw;
	private PlayCharacter c;
	private boolean attacking;
	private int rows;
	private JCheckBox specBox, perfectBox;
	private JTextField spentMotes, spentWillpower;
	private JComboBox<String> bodyParts, defenseBox, wepBox, abilBox, attackType, precBox, virtueBox;
	private List<Movement> movList;
	private JComboBox<String> damageTypeBox;

	public SimpleAttackSAW(SolveAttackWizard solveAttackWizard, boolean b,
			PlayCharacter ch) {
		saw 			= solveAttackWizard;
		c 				= ch;
		attacking	 	= b;
		
		rows 			= 10; //(b ? 10 : 9);
		
		int[] wEq 		= c.equip.getEquipWeaponsIndexes();
		String[] weps 	= new String[wEq.length];
		for (int i = 0; i < wEq.length; ++i)
			weps[i] = c.equip.weapon.get(wEq[i]).getName();
		
		movList 		= Movement.getAttackMovements();
		for (Movement m : c.movements) {
			movList.add(m);
		}
		
		int iMov 		= 0;
		String[] movs 	= new String[movList.size()];
		for (Movement m : movList) {
			movs[iMov++] = m.getName();
		}
		String[] types 	= {"Urto", "Letale", "Aggravato", "Mentale"};
		
		bodyParts		= new JComboBox<String>(HealthManager.NAME);
		defenseBox 		= new JComboBox<String>(CombatStatsPanel.defenses);
		wepBox 			= new JComboBox<String>(weps);
		attackType 		= new JComboBox<String>(movs);
		precBox 		= new JComboBox<String>(CombatStatsPanel.precisions);
		virtueBox 		= new JComboBox<String>(Virtues.name);
		damageTypeBox 	= new JComboBox<String>(types);
		abilBox 		= new JComboBox<String>(CombatStatsPanel.combatAbilities);
		specBox 		= new JCheckBox("Specialità applicabile");
		perfectBox 		= new JCheckBox(b ? "Attacco Perfetto" : "Difesa Perfetta");
		spentMotes 		= Format.getNumericTextField("0");
		spentWillpower 	= Format.getNumericTextField("0");
		
		// remove filled-up virtues
		for (int i = 3; i >= 0; --i) {
			if (c.pools.getVirtues().getStat((short) (i + 4)) == c.pools.getVirtues().getStat((short) i))
				virtueBox.removeItemAt(i);
		}
		
		virtueBox.setEnabled(false);
		defenseBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int x = defenseBox.getSelectedIndex();
				abilBox.setEnabled((x == 0) || (x == 2) || attacking);
				wepBox.setEnabled((x == 0) || (x == 2) || attacking);
			}
		});
		perfectBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				virtueBox.setEnabled(perfectBox.isSelected() && !attacking);
			}
		});
				
		remodel();
	}

	private void remodel() {
		removeAll();
		
		setLayout(new FlowLayout());
		JPanel labels = new JPanel(new GridLayout(rows, 1)), comboBoxes = new JPanel(new GridLayout(rows, 1)),
				inner = new JPanel(new BorderLayout());
		labels.add(new JLabel("Parte del corpo attaccata: "));
		comboBoxes.add(bodyParts);
		labels.add(new JLabel("Difesa utilizzata: "));
		comboBoxes.add(defenseBox);
		if (attacking) {
			labels.add(new JLabel("Precisione utilizzata: "));
			comboBoxes.add(precBox);
			labels.add(new JLabel("Tipo di azione: "));
			comboBoxes.add(attackType);
		} else {
			labels.add(new JLabel("Tipo di danno:"));
			comboBoxes.add(damageTypeBox);
		}
		labels.add(new JLabel("Arma utilizzata: "));
		comboBoxes.add(wepBox);
		labels.add(new JLabel("Abilità utilizzata:"));
		comboBoxes.add(abilBox);
		labels.add(specBox);
		comboBoxes.add(new JLabel());
		labels.add(perfectBox);
		comboBoxes.add(new JLabel());
		if (!attacking) {
			labels.add(new JLabel("Virtù dell'azione perfetta: "));
			comboBoxes.add(virtueBox);
		}
		labels.add(new JLabel("Moti spesi: "));
		comboBoxes.add(spentMotes);
		labels.add(new JLabel("Volontà spesa: "));
		comboBoxes.add(spentWillpower);
		
		inner.add(comboBoxes, BorderLayout.EAST);
		inner.add(labels, BorderLayout.CENTER);
		add(inner);
		revalidate();
	}

	@Override
	public void nextInvoked() {
		/* private JComboBox<String> bodyParts, defenseBox, wepBox, abilBox, attackType, precBox, virtueBox;*/
		saw.setValue(SolveAttackWizard.bodyPart, bodyParts.getSelectedIndex());
		saw.setValue(SolveAttackWizard.defenseVD, defenseBox.getSelectedIndex());
		saw.setValue(SolveAttackWizard.weaponUsed, wepBox.getSelectedIndex());
		saw.setValue(SolveAttackWizard.spentMotes, Integer.parseInt(spentMotes.getText().trim()));
		saw.setMovement(movList.get(attackType.getSelectedIndex()));
		saw.setValue(SolveAttackWizard.spentWillpower, Integer.parseInt(spentWillpower.getText().trim()));
		saw.setState(SolveAttackWizard.specialization, specBox.isSelected());
		saw.setState(SolveAttackWizard.perfectAction, perfectBox.isSelected());
		if (attacking) {
			saw.setValue(SolveAttackWizard.abilityUsed, CombatStatsPanel.abilMap[abilBox.getSelectedIndex()]);
			saw.setValue(SolveAttackWizard.damageType, getDamageType());
			saw.setValue(SolveAttackWizard.precision, precBox.getSelectedIndex());
			saw.addPane(SolveAttackWizard.PRECISION_ROLL, null);
		} else {
			saw.setValue(SolveAttackWizard.damageType, damageTypeBox.getSelectedIndex());
			saw.addPane(SolveAttackWizard.VD_SHOW, null);
			if (perfectBox.isSelected()) saw.setValue(SolveAttackWizard.defenseVirtue, virtueBox.getSelectedIndex());
			int x = abilBox.getSelectedIndex();
			if ((x == 1) || (x == 4) || (x == 3)) saw.setValue(SolveAttackWizard.abilityUsed, CombatStatsPanel.abilMap[x]);
			else if (x == 0) saw.setValue(SolveAttackWizard.abilityUsed, Abilities.DODGE);
			else saw.setValue(SolveAttackWizard.abilityUsed, Abilities.INTEGRITY);
			saw.addPane(SolveAttackWizard.VD_VISUALIZE, null);
		}
	}

	private int getDamageType() {
		Weapon w = c.equip.getEquipWeapon(wepBox.getSelectedIndex());
		int dType = 0;
		for (int i = 1; i < 4; ++i)
			if (w.getDamage(i) > 0) dType = i;
		return dType;
	}
	
	

}
