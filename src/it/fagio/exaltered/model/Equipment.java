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

package it.fagio.exaltered.model;

import it.fagio.exaltered.model.character.PlayCharacter;
import it.fagio.exaltered.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class Equipment {
	
	public List<Item> item;
	public List<Weapon> weapon;
	public List<Armor> armor;
	
	public int[] equippedArmor;
	public List<Integer> equippedWeapon;
	public List<Integer> equippedItems;
	public int weaponInUse;
	private PlayCharacter c;
		/*
	public Equipment(PlayCharacter ch) {
		c = ch;
		item = new ArrayList<Item>();
		armor = new ArrayList<Armor>();
		weapon = new ArrayList<Weapon>();
		equippedArmor = new int[6];
		for (int i = 0; i < 6; ++i) equippedArmor[i] = -1;
		equippedItems = new ArrayList<Integer>();
		equippedWeapon = new ArrayList<Integer>();
		weaponInUse = -1;
	}

	public List<TempEffect> getActiveEffects() {
		List<TempEffect> r = new ArrayList<TempEffect>();
		for (int i = 0; i < equippedItems.size(); ++i) {
			Item it = item.get(equippedItems.get(i));
			TempEffect[] x = it.getEffects();
			for (int j = 0; j < x.length; ++j)
				if (x[j].isActive()) r.add(x[j]);
			if (it.isArmonized()) {
				TempEffect[] x2 = it.getAttunedEffects();	
				for (int j = 0; j < x2.length; ++j)
					if (x2[j].isActive()) r.add(x2[j]);
			}
		}
		for (int i = 0; i < equippedWeapon.size(); ++i) {
			Weapon it = weapon.get(equippedWeapon.get(i));
			TempEffect[] x = it.getEffects();
			for (int j = 0; j < x.length; ++j)
				if (x[j].isActive()) r.add(x[j]);
			if (it.isArmonized()) {
				TempEffect[] x2 = it.getAttunedEffects();	
				for (int j = 0; j < x2.length; ++j)
					if (x2[j].isActive()) r.add(x2[j]);
			}
		}
		List<Integer> single = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) 
			if (!single.contains(equippedArmor[i]) && equippedArmor[i] > 0) {
				single.add(equippedArmor[i]);
				Armor it = armor.get(equippedArmor[i]);
				TempEffect[] x = it.getEffects();
				for (int j = 0; j < x.length; ++j)
					if (x[j].isActive()) r.add(x[j]);
				if (it.isArmonized()) {
					TempEffect[] x2 = it.getAttunedEffects();	
					for (int j = 0; j < x2.length; ++j)
						if (x2[j].isActive()) r.add(x2[j]);
				}
			}
		return r;
	}
	
	public float getTotalWeight() {
		float total = 0;
		
		for (int i = 0; i < item.size(); ++i)
			total += item.get(i).getWeight();
		for (int i = 0; i < weapon.size(); ++i)
			total += weapon.get(i).getWeight();
		for (int i = 0; i < armor.size(); ++i)
			total += armor.get(i).getWeight();
		
		return total;
	}

	public short getPhysicalDodgeDVPenalty() {
		return (short) (getArmorHindrance());
	}
	
	public int[] getEquipItemsIndexes() {
		return Sequences.toIntArray(equippedItems);
	}
	public int[] getEquipArmorsIndexes() {
		return equippedArmor;
	}
	public int[] getEquipWeaponsIndexes() {
		return Sequences.toIntArray(equippedWeapon);
	}
	public Item getEquipItem(int i) {
		return item.get(Sequences.toIntArray(equippedItems)[i]);
	}
	public Armor getEquipArmor(int i) {
		return armor.get(equippedArmor[i]);
	}
	public Weapon getEquipWeapon(int i) {
		return weapon.get(Sequences.toIntArray(equippedWeapon)[i]);
	}

	public short getArmorHindrance() {
		short r = 0;
		List<Integer> single = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			if (!single.contains(equippedArmor[i]) && equippedArmor[i] > 0) {
				single.add(equippedArmor[i]);
				r += armor.get(equippedArmor[i]).getStat(Armor.hindrance);
			}
		}
		return r;
	}

	public Item[] itemsToArray() {
		Item[] res = new Item[item.size()];
		for (int i = 0; i < res.length; ++i) {
			res[i] = item.get(i);
		}
		return res;
	}
	
	public Weapon[] weaponsToArray() {
		Weapon[] res = new Weapon[weapon.size()];
		for (int i = 0; i < res.length; ++i) {
			res[i] = weapon.get(i);
		}
		return res;
	}
	
	public Armor[] armorToArray() {
		Armor[] res = new Armor[armor.size()];
		for (int i = 0; i < res.length; ++i) {
			res[i] = armor.get(i);
		}
		return res;
	}
	
	public void addItem(Item i) {
		item.add(i);
	}

	public void addArmor(Armor i) {
		armor.add(i);
	}

	public void addWeapon(Weapon i) {
		weapon.add(i);
	}

	public void equipWeapon(int i) {
		if (equippedWeapon.contains(new Integer(i)))
			return;
		equippedWeapon.add(i);
	}
	
	public void unequipWeapon(int i) {
		equippedWeapon.remove(i);
	}
	
	public void dropWeapon(int i) {
		weapon.remove(i);
		Utils.log("Former eqWepLength = " + equippedWeapon.size() + ": " + Sequences.toString(equippedWeapon));
		for (int j = 0; j < equippedWeapon.size(); ++j ) {
			if (equippedWeapon.get(j) == i) equippedWeapon.remove(j);
			else if (equippedWeapon.get(j) > i) equippedWeapon.set(j, equippedWeapon.get(j) - 1);
		}
		Utils.log("Now eqWepLength = " + equippedWeapon.size() + ": " + Sequences.toString(equippedWeapon));
	}
	
	public void equipItem(int i) {
		equippedItems.add(i);
	}
	
	public void unequipItem(int i) {
		equippedItems.remove(i);
	}
	
	public void dropItem(int i) {
		item.remove(i);
		for (int j = 0; j < equippedItems.size(); ++j ) {
			if (equippedItems.get(j) == i) equippedItems.remove(j);
			else if (equippedItems.get(j) > i) equippedItems.set(j, equippedItems.get(j) - 1);
		}
	}
	
	public void equipArmor(int p) {
		if (p < 0) return;
		Armor a = armor.get(p);
		short[] pos = a.getBodyParts();
		for(int i = 0; i < pos.length; ++i) {			
			if (equippedArmor[pos[i]] != p)
				unequipArmor(equippedArmor[pos[i]]);
			equippedArmor[pos[i]] = p;
		}
	}
	
	public void unequipArmor(int p) {
		if (p < 0) return;
		Armor a = armor.get(p);
		short[] pos = a.getBodyParts();
		for(int i = 0; i < pos.length; ++i)
			equippedArmor[pos[i]] = -1;
	}
	
	public void dropArmor(int i) {
		if (i < 0) return;
		unequipArmor(i);
		armor.remove(i);
	}

	public short getPhysParryDVPenalty(int i) {
		return getEquipWeapon(i).getStat(Weapon.phisDefBonus);
	}
	public short getMenParryDVPenalty(int i) {
		return getEquipWeapon(i).getStat(Weapon.menDefBonus);
	}
	public short getPhysParryDVPenalty() {
		if (weaponInUse < 0) return 0;
		if (weaponInUse < equippedWeapon.size()) return getPhysParryDVPenalty(weaponInUse);
		return 0;
	}
	public short getMenParryDVPenalty() {
		if (weaponInUse < 0) return 0;
		if (weaponInUse < equippedWeapon.size()) return getMenParryDVPenalty(weaponInUse);
		return 0;
	}
	
	public void setWeaponInUse(int w) {
		weaponInUse = w;
	}
	public int getWeaponInUse() {
		return weaponInUse;
	}

	public short[] getArmorSoak(int position) {
		return armor.get(equippedArmor[position]).getSoak();
	}

	public void attuneItem(int index, boolean b) {
		item.get(equippedItems.get(index)).attune();
		c.pools.cacheEssence(item.get(equippedItems.get(index)).getEssence(), b, item.get(equippedItems.get(index)).getName());
	}

	public void attuneArmor(int index, boolean b) {
		c.pools.cacheEssence(armor.get(index).getEssence(), b, armor.get(index).getName());
		armor.get(index).attune();
	}

	public void attuneWeapon(int index, boolean b) {
		weapon.get(equippedWeapon.get(index)).attune();
		c.pools.cacheEssence(weapon.get(equippedWeapon.get(index)).getEssence(), b, weapon.get(equippedWeapon.get(index)).getName());
	}

	public void disattuneItem(int index) {
		int x = equippedItems.get(index);
		item.get(x).disattune();
		c.pools.decacheEssence(item.get(x).getEssence(), item.get(x).getName());
	}

	public void disattuneArmor(int x) {
		armor.get(x).disattune();
		c.pools.decacheEssence(armor.get(x).getEssence(), armor.get(x).getName());
	}

	public void disattuneWeapon(int index) {
		int x = equippedWeapon.get(index);
		weapon.get(x).disattune();
		c.pools.decacheEssence(weapon.get(x).getEssence(), weapon.get(x).getName());
	}

	public short getArmorStance() {
		short stance = 0;
		List<Integer> single = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			if (!single.contains(equippedArmor[i]) && equippedArmor[i] > 0) {
				single.add(equippedArmor[i]);
				if (armor.get(equippedArmor[i]).isArmonized())
					stance += armor.get(equippedArmor[i]).getStat(Armor.stance);
			}
		}
		return stance;
	}

	public short getPhysicalPrecision(int i) {
		return getEquipWeapon(i).getStat(Weapon.phisPrecBonus);
	}
	public short getMentalPrecision(int i) {
		return getEquipWeapon(i).getStat(Weapon.menPrecBonus);
	}
	public short getPhysicalPrecision() {
		if (weaponInUse < 0) return 0;
		if (weaponInUse < equippedWeapon.size()) return getPhysicalPrecision(weaponInUse);
		return 0;
	}
	public short getMentalPrecision() {
		if (weaponInUse < 0) return 0;
		if (weaponInUse < equippedWeapon.size()) return getMentalPrecision(weaponInUse);
		return 0;
	}

	public short getWeaponStance() {
		if (weaponInUse < 0) return 0;
		if (weaponInUse < equippedWeapon.size()) return getWeaponStance(weaponInUse);
		return 0;
	}
	public short getWeaponStance(int i) {
		return getEquipWeapon(i).getStat(Weapon.stance);
	}

	public int[] getEquippedArmor() {
		List<Integer> single = new ArrayList<Integer>();
		for (int i = 0; i < 6; ++i) {
			if (!single.contains(equippedArmor[i]) && equippedArmor[i] >= 0) {
				single.add(equippedArmor[i]);
			}
		}
		return Sequences.intFromList(single);
	}*/
	
}
