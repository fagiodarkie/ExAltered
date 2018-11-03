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

package it.apteroscode.exaltered.swing.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import it.apteroscode.exaltered.core.model.equipment.Armor;
import it.apteroscode.exaltered.core.model.equipment.Weapon;
import it.apteroscode.exaltered.swing.simple.GUITextAttribute;
import it.darkfagio.utils.Sequences;
import it.fagio.exaltered.model.PlayCharacter;

public class EquipmentPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private MainPanel main;
	private PlayCharacter c;

	private Item[] items;
	private Weapon[] weapons;
	private Armor[] armor;
	private int[] eqItems, eqWeapons, eqArmors;
	private JPanel itemsPanel;

	public JList<String> itemList, weaponList, armorList, eqItemList, eqWeaponList, eqArmorList;
	
	public EquipmentPanel(MainPanel mainPanel, PlayCharacter c) {
		this.c = c;
		main = mainPanel;
		
		remodel();
	}
	
	public void remodel() {
		
		removeAll();
		setLayout(new BorderLayout());
		
		itemsPanel = new JPanel(new GridLayout(3, 1, 10, 10));
		
		JPanel itemRow = new JPanel(new BorderLayout()),
				weaponsRow = new JPanel(new BorderLayout()),
				armorRow = new JPanel(new BorderLayout());

		items = c.getEquip().itemsToArray();
		weapons = c.getEquip().weaponsToArray();
		armor = c.getEquip().armorToArray();
		eqItems = Sequences.intFromList(c.getEquip().equippedItems);
		eqWeapons = Sequences.intFromList(c.getEquip().equippedWeapon);
		eqArmors = c.getEquip().getEquippedArmor();
		
		
		String[] itStrings = new String[items.length], wepStrings = new String[weapons.length],
				armStrings = new String[armor.length], eqItmStrings = new String[eqItems.length],
				eqArmrStrings = new String[eqArmors.length], eqWepStrings = new String[eqWeapons.length];
		
		for (int i = 0; i < items.length; ++i) {
			itStrings[i] = items[i].getName();
		}
		for (int i = 0; i < weapons.length; ++i) {
			wepStrings[i] = weapons[i].getName();
		}
		for (int i = 0; i < armor.length; ++i) {
			armStrings[i] = armor[i].getName();
		}
		for (int i = 0; i < eqItems.length; ++i) {
			eqItmStrings[i] = items[eqItems[i]].getName();
			itStrings[eqItems[i]] += " (E)";
			if (items[eqItems[i]].isArmonized()) eqItmStrings[i] += " (A)";
		}
		for (int i = 0; i < eqWeapons.length; ++i) {
			int k = eqWeapons[i];
			eqWepStrings[i] = weapons[k].getName();
			wepStrings[eqWeapons[i]] += " (E)";
			if (weapons[eqWeapons[i]].isArmonized()) eqWepStrings[i] += " (A)";
		}
		for (int i = 0; i < eqArmors.length; ++i) {
			eqArmrStrings[i] = armor[eqArmors[i]].getName();
			armStrings[eqArmors[i]] += " (E)";
			if (armor[eqArmors[i]].isArmonized()) eqArmrStrings[i] += " (A)";
		}
		
		itemList = new JList<String>(itStrings);
		weaponList = new JList<String>(wepStrings);
		armorList = new JList<String>(armStrings);
		eqItemList = new JList<String>(eqItmStrings);
		eqArmorList = new JList<String>(eqArmrStrings);
		eqWeaponList = new JList<String>(eqWepStrings); 
				 
		itemList.setBorder(BorderFactory.createTitledBorder("Oggetti generici"));
		itemList.setPreferredSize(new Dimension(400, itemList.getPreferredSize().height));
		weaponList.setBorder(BorderFactory.createTitledBorder("Armi"));
		weaponList.setPreferredSize(new Dimension(400, itemList.getPreferredSize().height));
		armorList.setBorder(BorderFactory.createTitledBorder("Armature"));
		armorList.setPreferredSize(new Dimension(400, itemList.getPreferredSize().height));
		eqItemList.setBorder(BorderFactory.createTitledBorder("Oggetti Equipaggiati"));
		eqItemList.setPreferredSize(new Dimension(400, itemList.getPreferredSize().height));
		eqArmorList.setBorder(BorderFactory.createTitledBorder("Armature Equipaggiate"));
		eqArmorList.setPreferredSize(new Dimension(400, itemList.getPreferredSize().height));
		eqWeaponList.setBorder(BorderFactory.createTitledBorder("Armi Equipaggiate"));
		eqWeaponList.setPreferredSize(new Dimension(400, itemList.getPreferredSize().height));
		
		SwitchCreateButtons itemSwitcher = new SwitchCreateButtons(this, c, SwitchCreateButtons.ITEM),
				armorSwitcher = new SwitchCreateButtons(this, c, SwitchCreateButtons.ARMOR),
				weaponSwitcher = new SwitchCreateButtons(this, c, SwitchCreateButtons.WEAPON);
		JPanel itemSwitcherWrapper = new JPanel(new FlowLayout()),
				armorSwitcherWrapper  = new JPanel(new FlowLayout()),
				weaponSwitcherWrapper = new JPanel(new FlowLayout());
		
		itemSwitcherWrapper.add(itemSwitcher);
		armorSwitcherWrapper.add(armorSwitcher);
		weaponSwitcherWrapper.add(weaponSwitcher);
		
		itemRow.add(eqItemList, BorderLayout.WEST);
		itemRow.add(itemSwitcherWrapper, BorderLayout.CENTER);
		itemRow.add(itemList, BorderLayout.EAST);
		weaponsRow.add(eqWeaponList, BorderLayout.WEST);
		weaponsRow.add(weaponSwitcherWrapper, BorderLayout.CENTER);
		weaponsRow.add(weaponList, BorderLayout.EAST);
		armorRow.add(eqArmorList, BorderLayout.WEST);
		armorRow.add(armorSwitcherWrapper, BorderLayout.CENTER);
		armorRow.add(armorList, BorderLayout.EAST);
		
		itemsPanel.add(itemRow);
		itemsPanel.add(weaponsRow);
		itemsPanel.add(armorRow);
		
		add(itemsPanel, BorderLayout.CENTER);
		
		
		
		JPanel footer = new JPanel(new GridLayout(1, 2, 20, 0)),
				footLeft = new JPanel(new GridLayout(4, 1)), footRight = new JPanel(new GridLayout(4, 1));
		
		footLeft.add(new GUITextAttribute("Massimale:", c.calc.getMaximal()));
		footLeft.add(new GUITextAttribute("Peso Massimo Trasp.le:", c.calc.getCMST()));
		String s = Double.toString(c.calc.getLoadPercentage());
		if (s.length() > 5) s = s.substring(0, 5);
		footLeft.add(new GUITextAttribute("Percentuale di carico:",	s + " %"));
		footLeft.add(new GUITextAttribute("Penalit� di carico:", c.calc.getLoadMovementPenalty()));

		footRight.add(new GUITextAttribute("Peso trasportato:", c.equip.getTotalWeight()));
		footRight.add(new JLabel("Arma principale:"));
		final JComboBox<String> mainWeapons = new JComboBox<String>(eqWepStrings);
		mainWeapons.addItem("(nessuna)");
		if (c.equip.getWeaponInUse() < 0) mainWeapons.setSelectedIndex(eqWepStrings.length);
		else mainWeapons.setSelectedIndex(c.equip.getWeaponInUse());
		mainWeapons.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (((String) mainWeapons.getSelectedItem()).equalsIgnoreCase("(nessuna)"))
					c.equip.setWeaponInUse(-1);
				else c.equip.setWeaponInUse(mainWeapons.getSelectedIndex());
				remodel();
			}
		});
		footRight.add(mainWeapons);
		footRight.add(new GUITextAttribute("Penalit� minimi dell'arma:",
				c.calc.getMinimumNotReachedPenalty(c.equip.getWeaponInUse())));
		
		footer.add(footLeft);
		footer.add(footRight);

		add(footer, BorderLayout.SOUTH);
		
		validate();
	}
	
	public void addItem(Item i) {
		c.getEquip().addItem(i);
		notifyChange();
	}
	
	public void notifyChange() {
		main.notifyChange();
		remodel();
	}

}
