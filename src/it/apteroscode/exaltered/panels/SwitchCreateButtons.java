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

package it.apteroscode.exaltered.panels;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import it.apteroscode.exaltered.model.equipment.Armor;
import it.apteroscode.exaltered.model.equipment.Weapon;
import it.apteroscode.exaltered.wizard.AddArmorWizard;
import it.apteroscode.exaltered.wizard.AddWeaponWizard;
import it.fagio.exaltered.model.PlayCharacter;
import it.apteroscode.exaltered.wizard.AddItemWizard;
import it.darkfagio.utils.gui.DialogPanel;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class SwitchCreateButtons extends JPanel {
	private static final long serialVersionUID = 1L;
	public static final int ITEM = 0;
	public static final int WEAPON = 1;
	public static final int ARMOR = 2;
	private int type;
	private PlayCharacter ch;
	private EquipmentPanel ePanel;
	
	public static String[] objectName = {"Oggetto", "Arma", "Armatura"};
	
	private class ButtonDialogListener implements ActionListener {
		
		EquipmentPanel p;
		boolean mod;
		DialogPanel component;
		
		public ButtonDialogListener(EquipmentPanel parent, boolean modify) {
			p = parent;
			mod = modify;
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
			JDialog dialog = new JDialog();
			if (type == ITEM) {
				component = new AddItemWizard(ePanel, ch, mod);
			} else if (type == ARMOR) {
				component = new AddArmorWizard(ePanel, ch, mod);
			} else {
				ch.equip.setWeaponInUse(-1);;
				component = new AddWeaponWizard(ePanel, ch, mod);
			}
			dialog.add(component);
			component.setDialog(dialog);
			dialog.setMinimumSize(component.getMinimumSize());
			dialog.pack();
			dialog.setVisible(true);
			dialog.setModal(true);
			dialog.addWindowListener(new WindowListener() {
				
				@Override
				public void windowOpened(WindowEvent arg0) {}
				
				@Override
				public void windowIconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeiconified(WindowEvent arg0) {}
				
				@Override
				public void windowDeactivated(WindowEvent arg0) {}
				
				@Override
				public void windowClosing(WindowEvent arg0) {}
				
				@Override
				public void windowClosed(WindowEvent arg0) {
					p.remodel();
				}
				
				@Override
				public void windowActivated(WindowEvent arg0) {}
			});
		}
	}

	
	public SwitchCreateButtons(EquipmentPanel equipmentPanel, PlayCharacter c,
			int armor2) {
		this.ePanel = equipmentPanel;
		this.ch = c;
		this.type = armor2;
		
		remodel();
	}
	
	public void remodel() {
		setLayout(new GridLayout(6, 1));
				
		JButton add = new JButton("+"), equip = new JButton("<"), remove = new JButton("-"),
				modify = new JButton("*"), unequip = new JButton(">"), armonize = new JButton("�");
		add.setToolTipText("Aggiungi " + objectName[type]);
		equip.setToolTipText("Equipaggia " + objectName[type]);
		remove.setToolTipText("Rimuovi " + objectName[type]);
		modify.setToolTipText("Modifica " + objectName[type]);
		unequip.setToolTipText("Riponi " + objectName[type]);
		armonize.setToolTipText("Armonizza " + objectName[type]);
		
		add.addActionListener(new ButtonDialogListener(ePanel, false));
		modify.addActionListener(new ButtonDialogListener(ePanel, true));
		
		switch(type) {
		case ITEM: {
			remove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int x = ePanel.itemList.getSelectedIndex();
					String name = ePanel.itemList.getSelectedValue();
					int yesNo = JOptionPane.showConfirmDialog(null, "Eliminare l'oggetto (" + name +  ")?",
							"Eliminazione...", JOptionPane.YES_NO_OPTION);
					if (yesNo == JOptionPane.NO_OPTION) return;
					ch.equip.dropItem(x);
					ePanel.notifyChange();
				}
			});
			equip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToEquip = ePanel.itemList.getSelectedIndex();
					if (indexToEquip < 0) return;
					ch.equip.equipItem(indexToEquip);
					ePanel.notifyChange();
				}
			});
			unequip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToUnequip = ePanel.eqItemList.getSelectedIndex();
					if (indexToUnequip < 0) return;
					ch.equip.unequipItem(indexToUnequip);
					ePanel.notifyChange();
				}
			});
			armonize.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToToggle = ePanel.eqItemList.getSelectedIndex();
					if (indexToToggle < 0) return;
					Item it = ch.getEquip().item.get(ch.getEquip().equippedItems.get(indexToToggle));
					if (!it.isArmonized() ) {
						Object[] options = {"Riserva Personale",
			                    "Riserva Periferica",
			                    "Non armonizzare"};
						int n = JOptionPane.showOptionDialog(null,
						    "A che riserva di moti armonizzare?",
						    "Armonizzazione",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[2]);
						if (n == JOptionPane.CANCEL_OPTION) return;
						ch.equip.attuneItem(indexToToggle, (n == JOptionPane.NO_OPTION));
					}
					else ch.equip.disattuneItem(indexToToggle);
					ePanel.notifyChange();
				}
			});
			break;
		}
		case WEAPON: {
			remove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int x = ePanel.weaponList.getSelectedIndex();
					if (x < 0) return;
					String name = ePanel.weaponList.getSelectedValue();
					int yesNo = JOptionPane.showConfirmDialog(null, "Eliminare l'arma (" + name +  ")?",
							"Eliminazione...", JOptionPane.YES_NO_OPTION);
					if (yesNo == JOptionPane.NO_OPTION) return;
					for (int i = 0; i < ch.equip.equippedWeapon.size(); ++i) {
						if (ch.equip.equippedWeapon.get(i) == x) {
							ch.equip.unequipWeapon(i);
							break;
						}
					}
					ch.equip.dropWeapon(x);
					ePanel.notifyChange();
				}
			});
			equip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToEquip = ePanel.weaponList.getSelectedIndex();
					if (indexToEquip < 0) return;
					ch.equip.equipWeapon(indexToEquip);
					ePanel.notifyChange();
				}
			});
			armonize.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToToggle = ePanel.eqWeaponList.getSelectedIndex();
					if (indexToToggle < 0) return;
					Weapon w = ch.getEquip().weapon.get(ch.getEquip().equippedWeapon.get(indexToToggle));
					if (!w.isArmonized() ) {
						Object[] options = {"Riserva Personale",
			                    "Riserva Periferica",
			                    "Non armonizzare"};
						int n = JOptionPane.showOptionDialog(null,
						    "A che riserva di moti armonizzare?",
						    "Armonizzazione",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[2]);
						if (n == JOptionPane.CANCEL_OPTION) return;
						ch.equip.attuneWeapon(indexToToggle, (n == JOptionPane.NO_OPTION));
					}
					else ch.equip.disattuneWeapon(indexToToggle);
					ePanel.notifyChange();
				}
			});
			unequip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToUnequip = ePanel.eqWeaponList.getSelectedIndex();
					if (indexToUnequip < 0) return;
					Weapon w = ch.equip.weapon.get(ch.equip.equippedWeapon.get(indexToUnequip));
					if (w.isArmonized())
						ch.equip.disattuneWeapon(indexToUnequip);
					ch.equip.unequipWeapon(indexToUnequip);
					ePanel.notifyChange();
				}
			});
			break;
		}
		case ARMOR: {
			remove.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					if (ePanel.armorList.getSelectedIndex() < 0) return;
					String name = ePanel.itemList.getSelectedValue();
					int yesNo = JOptionPane.showConfirmDialog(null, "Eliminare l'armatura (" + name +  ")?",
							"Eliminazione...", JOptionPane.YES_NO_OPTION);
					if (yesNo == JOptionPane.NO_OPTION) return;
					int indexToUnequip = -1;
					for (int i = 0; i < ch.equip.armor.size(); ++i)
						if (ch.equip.armor.get(i).getName().equalsIgnoreCase(name)) {
							indexToUnequip = i;
							break;
						}
					if (indexToUnequip < 0) return;
					if (ch.equip.armor.get(indexToUnequip).isArmonized())
						ch.equip.disattuneArmor(indexToUnequip);
					ch.equip.dropArmor(indexToUnequip);
					ePanel.notifyChange();
				}
			});
			equip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToEquip = ePanel.armorList.getSelectedIndex();
					if (indexToEquip < 0) return;
					ch.equip.equipArmor(indexToEquip);
					ePanel.notifyChange();
				}
			});
			unequip.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToUnequip = ePanel.eqArmorList.getSelectedIndex();
					if (indexToUnequip < 0) return;
					indexToUnequip = -1;
					String name = ePanel.eqArmorList.getSelectedValue();
					for (int i = 0; i < ch.equip.armor.size(); ++i)
						if (ch.equip.armor.get(i).getName().equalsIgnoreCase(name)) {
							indexToUnequip = i;
							break;
						}
					if (indexToUnequip < 0) return;
					Armor a = ch.equip.armor.get(indexToUnequip);
					if (a.isArmonized()) {
						ch.equip.disattuneArmor(indexToUnequip);
					}
					ch.equip.unequipArmor(indexToUnequip);
					ePanel.notifyChange();
				}
			});
			armonize.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int indexToToggle = ePanel.eqArmorList.getSelectedIndex();
					if (indexToToggle < 0) return;
					String name = ePanel.eqItemList.getSelectedValue();
					indexToToggle = -1;
					for (int i = 0; i < ch.equip.equippedItems.size(); ++i)
						if (ch.equip.item.get(i).getName().equalsIgnoreCase(name)) {
							indexToToggle = i;
							break;
						}
					if (indexToToggle < 0) return;
					Armor a = ch.getEquip().armor.get(indexToToggle);
					if (!a.isArmonized()) {
						Object[] options = {"Riserva Personale",
			                    "Riserva Periferica",
			                    "Non armonizzare"};
						int n = JOptionPane.showOptionDialog(null,
						    "A che riserva di moti armonizzare?",
						    "Armonizzazione",
						    JOptionPane.YES_NO_CANCEL_OPTION,
						    JOptionPane.QUESTION_MESSAGE,
						    null,
						    options,
						    options[2]);
						if (n == JOptionPane.CANCEL_OPTION) return;
						ch.equip.attuneItem(indexToToggle, (n == JOptionPane.NO_OPTION));
					}
					else ch.equip.disattuneArmor(indexToToggle);
					ePanel.notifyChange();
				}
			});
			break;
		}
		}
		
		add(equip);
		add(add);
		add(remove);
		add(modify);
		add(armonize);
		add(unequip);
		
	}

}
