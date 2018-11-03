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

import it.fagio.exaltered.utils.Utils;

public class Calculator {

	private PlayCharacter c;
	
	private double[][] physK = 
		{{0.7, 1, 0.3, 1, 0.3, 0.2},
		{ 0.3, 1, 0.2, 1, 0.7, 0.3},
		{ 1, 0.7, 0.3, 1, 0.3, 0.2},
		{ 0.7, 1, 0.2, 1, 0.3, 0.3},
		{ 0.3, 1, 0.2, 0.7, 1, 0.3}};
	
	private double[][] mentalK = 
		{{1, 0.2, 0.3, 0.7, 1, 0.3},
		{ 0.2, 1, 0.3, 0.7, 1, 0.3},
		{ 0.2, 0.2, 0.5, 0.7, 0.4, 1},
		{ 1, 0.3, 0.6, 1, 0.2, 0.4},
		{ 0.3, 1, 0.6, 1, 0.2, 0.4}};


	
	public Calculator(PlayCharacter playCharacter) {
		c = playCharacter;
	}
	
	public int getMinimumNotReachedPenalty(int weaponIndex) {
		
		if (weaponIndex < 0 || weaponIndex > c.equip.equippedWeapon.size()) return 0;

		int res = 0;
		
		Minimum[] m = c.equip.weapon.get(c.equip.equippedWeapon.get(weaponIndex)).getMinimums();
		for (int i = 0; i < m.length; ++i) {
			int ab = m[i].getStat();
			short val = 0;
			if (ab < 10) val = c.atts.getAttribute(ab);
			else if (ab < 40) val = c.abils.getAbility(ab - 10);
			else {
				switch (ab) {
				case TempEffect.WILLPOWER:
					val = c.pools.getStat(Pools.willpower);
					break;
				case TempEffect.PERMANENTESSENCE:
					val = c.pools.getStat(Pools.permanentEssence);
					break;
				case TempEffect.PERIPHERALESSENCE:
					val = c.pools.getPeripheralEssence();
					break;
				case TempEffect.PERSONALESSENCE:
					val = c.pools.getPersonalEssence();
					break;
				case TempEffect.VALOR:
					val = c.pools.getVirtues().getStat(Virtues.valor);
					break;
				case TempEffect.COMPASSION:
					val = c.pools.getVirtues().getStat(Virtues.compassion);
					break;
				case TempEffect.TEMPERANCE:
					val = c.pools.getVirtues().getStat(Virtues.temperance);
					break;
				case TempEffect.CONVINCTION:
					val = c.pools.getVirtues().getStat(Virtues.convinction);
					break;

				default:
					break;
				}
			}
			res += m[i].getPenalty(val); 
		}
		
		return res;
	}
		
	public short[] getNaturalSoak() {
		short[] res = {c.atts.getAttribute(Attributes.STAMINA),
				(short) ((c.atts.getAttribute(Attributes.STAMINA) + 1) / 2), 0, 0}; 
		return res;
	}
	public short getMovement() {
		short amount = (short) (c.atts.getAttribute(Attributes.DEXTERITY) + c.health.getDVPenalty() + c.effects.getBonus(TempEffect.MOVEMENT)
				+ getLoadMovementPenalty());
		return (amount > 1 ? amount : 1);
	}
	public int getSpeedPenalty() {
		float load = getLoadPercentage();
		
		if (load > 87.5) return 1;
		else return 0;
	}
	public int getLoadMovementPenalty() {
		/* Le penalit� (o bonus) al movimento si applica:
		1. Alle riserve di dadi in prove di agilit�, destrezza e simili.
		2. Alla distanza che puoi percorrere in ogni tick, determinabile con la tua destrezza.
			Ad ogni modo puoi comunque muoverti di almeno 1 metro per tick, anche se la penalit� dovesse
			azzerare la tua distanza oppure otterresti un numero negativo.
		3. Alla VD di schivata fisica. */
		float load = getLoadPercentage();		
		if (load < 5) return 2;
		else if (load < 25) return 1;
		else if (load > 75) return -3;
		else if (load > 62.5) return -2;
		else if (load > 50) return -1;
		else return 0;
	}
	
	public float getCMST() {
		// this is liftable
		return getMaximal() * c.atts.getAttribute(Attributes.STAMINA) / 10;
	}
	public float getMaximal() {
		int ability = (c.atts.getAttribute(Attributes.STRENGTH) + c.abils.getAbility(Abilities.RESISTANCE)), maximal = 0;
		if (ability < 3) maximal = 40 * ability;
		else if (ability < 8) maximal = 50 * ability - 25;
		else if (ability < 16) maximal = (ability - 4) * 100;
		else maximal = 1000 + ((ability - 15) * 250);
		return maximal;

	}
	public float getLoadPercentage() {
		// load percentage is carried weight percent liftable
		return (c.equip.getTotalWeight() / getCMST()) * 100;
	}
	public short[] getSoak() {
		short[] r = getNaturalSoak();
		r[0] += c.effects.getBonus(TempEffect.BASHSOAK);
		r[1] += c.effects.getBonus(TempEffect.LETHALSOAK);
		r[2] += c.effects.getBonus(TempEffect.AGGRAVATEDSOAK);
		r[3] += c.effects.getBonus(TempEffect.MENTALSOAK);
		
		return r;
	}
	
	public short[] getArmoredSoak(int position) {
		short[] r = {0, 0, 0, 0};
		int x = c.equip.equippedArmor[position];
		if (x < 0) return r;
		r = c.equip.armor.get(x).getSoak();
		for (int i = 0; i < 3; ++i)
			r[i] += c.effects.getBonus(TempEffect.ARBASHSOAKHEAD + position + 6*i);
		r[3] += c.effects.getBonus(TempEffect.ARMENTALSOAK);
		return r;
	}
	
	public short getPrecisionMeleeStrength(boolean withSpec) {
		return (short) (getKPrecisionMeleeStrength() + c.abils.getAbility(c.abils.getPhysicalCombatAbility())
				+ (withSpec ? c.abils.getSpecArray()[c.abils.getPhysicalCombatAbility()] : 0)
				+ getPrecisionBonus());
	}
	public short getPrecisionMeleeDex(boolean withSpec) {
		return (short) (getKPrecisionMeleeDex() + c.abils.getAbility(c.abils.getPhysicalCombatAbility())
				+ (withSpec ? c.abils.getSpecArray()[c.abils.getPhysicalCombatAbility()] : 0)
				+ getPrecisionBonus());
	}
	public short getPrecisionDistance(boolean withSpec) {
		return (short) (getKPrecisionDistance() + c.abils.getAbility(c.abils.getPhysicalCombatAbility())
				+ (withSpec ? c.abils.getSpecArray()[c.abils.getPhysicalCombatAbility()] : 0)
				+ getPrecisionBonus());
	}
	public short getPrecisionMan(boolean withSpec) {
		return (short) (getKPrecisionMan() + c.abils.getAbility(c.abils.getMentalCombatAbility())
				+ (withSpec ? c.abils.getSpecArray()[c.abils.getMentalCombatAbility()] : 0)
				+ getMentalPrecisionBonus());
	}
	public short getPrecisionChar(boolean withSpec) {
		return (short) (getKPrecisionChar() + c.abils.getAbility(c.abils.getMentalCombatAbility())
				+ (withSpec ? c.abils.getSpecArray()[c.abils.getMentalCombatAbility()] : 0)
				+ getMentalPrecisionBonus());
	}

	public short getPhysicalParry(int position, boolean withSpec) {
		return (short) ((getKPhysicalParry() + c.abils.getAbility(c.abils.getPhysicalCombatAbility())
				+ 1 + (withSpec ? c.abils.getSpecArray()[c.abils.getPhysicalCombatAbility()] : 0)
				+ c.equip.getPhysParryDVPenalty()) / 2
				+ getParryBonus(position));
	}
	public short getPhysicalParry(int position, boolean withSpec, int weapon) {
		return (short) ((getKPhysicalParry() + c.abils.getAbility(c.abils.getPhysicalCombatAbility())
				+ 1 + (withSpec ? c.abils.getSpecArray()[c.abils.getPhysicalCombatAbility()] : 0)
				+ c.equip.getPhysParryDVPenalty(weapon)) / 2
				+ getParryBonus(position));
	}
	public short getPhysicalDodge(int position, boolean withSpec) {
		return (short) ((getKPhysicalDodge() + c.abils.getAbility(Abilities.DODGE)
				+ c.pools.getStat(Pools.permanentEssence) + 1 + (withSpec ? c.abils.getSpecArray()[Abilities.DODGE] : 0)) / 2
				+ getDodgeBonus(position));
	}	
	public short getMentalParryMan(boolean withSpec) {
		return (short) ((getKMentalParryMan() + c.abils.getAbility(c.abils.getMentalCombatAbility())
				+ 1 + (withSpec ? c.abils.getSpecArray()[c.abils.getMentalCombatAbility()] : 0)
				+ c.equip.getMenParryDVPenalty()) / 2
				+ getMentalParryBonus());
	}
	public short getMentalParryChar(boolean withSpec) {
		return (short) ((getKMentalParryChar() + c.abils.getAbility(c.abils.getMentalCombatAbility())
				+ 1 + (withSpec ? c.abils.getSpecArray()[c.abils.getMentalCombatAbility()] : 0)
				+ c.equip.getMenParryDVPenalty()) / 2
				+ getMentalParryBonus());
	}
	public short getMentalDodge(boolean withSpec) {
		return (short) ((getKMentalDodge() + c.abils.getAbility(Abilities.INTEGRITY)
				+ 1 + c.pools.getStat(Pools.willpower) + (withSpec ? c.abils.getSpecArray()[Abilities.INTEGRITY] : 0)) / 2
				+ getMentalDodgeBonus());
	}
	
	// ----------------- Bonuses

	public short getDodgeBonus(int position) {
		return (short) (c.health.getDodgePenalty(position) + c.health.getDVPenalty(position) + c.equip.getPhysicalDodgeDVPenalty()
				+ c.effects.getBonus(TempEffect.PHYSICALDODGEDVHEAD + position) + getLoadMovementPenalty());
	}
	public short getParryBonus(int position) {
		return (short) (c.health.getParryPenalty(position) + c.health.getDVPenalty(position) + c.equip.getPhysParryDVPenalty()
				+ c.effects.getBonus(TempEffect.PHYSICALPARRYDVHEAD + position));
	}
	public short getParryBonus(int position, int weaponIndex) {
		return (short) (c.health.getParryPenalty(position) + c.health.getDVPenalty(position) + c.equip.getPhysParryDVPenalty(weaponIndex)
				+ c.effects.getBonus(TempEffect.PHYSICALPARRYDVHEAD + position));
	}
	public short getPrecisionBonus() {
		return getPrecisionBonus(c.equip.getWeaponInUse());
	}
	public short getMentalParryBonus() {
		return (short) (c.health.getDVPenalty() + c.effects.getBonus(TempEffect.MENTALPARRYDV) + c.equip.getMenParryDVPenalty()); 
	}
	public short getPrecisionBonus(int weaponIndex) {
		 return (short) (c.health.getDVPenalty() + c.effects.getBonus(TempEffect.PHYSICALPRECISION)
				 + c.equip.getPhysicalPrecision(weaponIndex) + getMinimumNotReachedPenalty(weaponIndex));
	}
	public short getMentalParryBonus(int weaponIndex) {
		return (short) (c.health.getDVPenalty() + c.effects.getBonus(TempEffect.MENTALPARRYDV) + c.equip.getMenParryDVPenalty(weaponIndex)); 
	}
	public short getMentalDodgeBonus() {
		return (short) (c.health.getDVPenalty() + c.effects.getBonus(TempEffect.MENTALDODGEDV)); 
	}
	public short getMentalPrecisionBonus(int weaponIndex) {
		return (short) (c.health.getDVPenalty() + c.effects.getBonus(TempEffect.MENTALPRECISION)
				+ c.equip.getMentalPrecision(weaponIndex) + getMinimumNotReachedPenalty(weaponIndex));
	}
	public short getMentalPrecisionBonus() {
		return getMentalPrecisionBonus(c.equip.getWeaponInUse());
	}
	
	public short getParryBalance(boolean successful) {
		return (short) (c.atts.getAttribute(Attributes.STAMINA) + c.abils.getAbility(Abilities.RESISTANCE) + c.equip.getArmorStance()
				+ (successful ? c.equip.getWeaponStance() : 0));
	}
	public short getDodgeBalance() {
		return (short) (c.atts.getAttribute(Attributes.DEXTERITY) + c.abils.getAbility(Abilities.ATHLETICS));
	}

	public int getMinimumDamage() {
		return c.pools.getStat(Pools.permanentEssence) + c.effects.getBonus(TempEffect.MINIMUMDAMAGE);
	}

	public String[] getPartials(int value, boolean withSpec, int weapon, Movement mov, int abil, int flurryPenalty) {
		String[] r = new String[7];
		
		switch(value) {
		case 0: {
			// Melee (STR) 
			r[0] = "" + (getPrecisionMeleeStrength(withSpec) - flurryPenalty);
			r[1] = "" + c.effects.getBonus(TempEffect.PHYSICALPRECISION);
			r[2] = "" + c.equip.weapon.get(weapon).getStat(Weapon.phisPrecBonus);
			r[3] = "" + mov.getBonus(TempEffect.PHYSICALPRECISION);
			r[4] = "" + getKPrecisionMeleeStrength();
			r[5] = "" + (c.abils.getAbility(abil));
			r[6] = "" + flurryPenalty;
			
			break;
		}
		case 1: {
			// Melee (DEX)
			r[0] = "" + (getPrecisionMeleeDex(withSpec) - flurryPenalty);
			r[1] = "" + c.effects.getBonus(TempEffect.PHYSICALPRECISION);
			r[2] = "" + c.equip.weapon.get(weapon).getStat(Weapon.phisPrecBonus);
			r[3] = "" + mov.getBonus(TempEffect.PHYSICALPRECISION);
			r[4] = "" + getKPrecisionMeleeDex();
			r[5] = "" + (c.abils.getAbility(abil));
			r[6] = "" + flurryPenalty;
			
			break;
		}
		case 2: {
			// Distance
			r[0] = "" + (getPrecisionDistance(withSpec) - flurryPenalty);
			r[1] = "" + c.effects.getBonus(TempEffect.PHYSICALPRECISION);
			r[2] = "" + c.equip.weapon.get(weapon).getStat(Weapon.phisPrecBonus);
			r[3] = "" + mov.getBonus(TempEffect.PHYSICALPRECISION);
			r[4] = "" + getKPrecisionMeleeDex();
			r[5] = "" + (c.abils.getAbility(abil));
			r[6] = "" + flurryPenalty;
			
			break;
		}
		case 3: {
			// Mental (CHA)
			r[0] = "" + (getPrecisionChar(withSpec) - flurryPenalty);
			r[1] = "" + c.effects.getBonus(TempEffect.MENTALPRECISION);
			r[2] = "" + c.equip.weapon.get(weapon).getStat(Weapon.menPrecBonus);
			r[3] = "" + mov.getBonus(TempEffect.MENTALPRECISION);
			r[4] = "" + getKPrecisionChar();
			r[5] = "" + (c.abils.getAbility(abil));
			r[6] = "" + flurryPenalty;
			
			break;
		}
		case 4: {
			// Mental (MAN)
			r[0] = "" + (getPrecisionMan(withSpec) - flurryPenalty);
			r[1] = "" + c.effects.getBonus(TempEffect.MENTALPRECISION);
			r[2] = "" + c.equip.weapon.get(weapon).getStat(Weapon.menPrecBonus);
			r[3] = "" + mov.getBonus(TempEffect.MENTALPRECISION);
			r[4] = "" + getKPrecisionMan();
			r[5] = "" + (c.abils.getAbility(abil));
			r[6] = "" + flurryPenalty;
			
			break;
		}
		}
		
		r[4] = r[4].substring(0, 7);
		
		return r;
	}
	
	public double getKPhysicalParry() {
		double[] attribs = {c.atts.getAttribute(0), c.atts.getAttribute(1), c.atts.getAttribute(2), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, physK[0], 6));	
	}
	
	public double getKPhysicalDodge() {
		double[] attribs = {c.atts.getAttribute(0), c.atts.getAttribute(1), c.atts.getAttribute(2), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, physK[1], 6));	
	}

	public double getKPrecisionMeleeStrength() {
		double[] attribs = {c.atts.getAttribute(0), c.atts.getAttribute(1), c.atts.getAttribute(2), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, physK[2], 6));	
	}
	public double getKPrecisionMeleeDex() {
		double[] attribs = {c.atts.getAttribute(0), c.atts.getAttribute(1), c.atts.getAttribute(2), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, physK[3], 6));	
	}
	public double getKPrecisionDistance() {
		double[] attribs = {c.atts.getAttribute(0), c.atts.getAttribute(1), c.atts.getAttribute(2), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, physK[4], 6));	
	}

	
	public double getKMentalParryChar() {
		double[] attribs = {c.atts.getAttribute(3), c.atts.getAttribute(4), c.atts.getAttribute(5), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, mentalK[0], 6));	
	}
	
	public double getKMentalParryMan() {
		double[] attribs = {c.atts.getAttribute(3), c.atts.getAttribute(4), c.atts.getAttribute(5), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, mentalK[1], 6));	
	}

	public double getKMentalDodge() {
		double[] attribs = {c.atts.getAttribute(3), c.atts.getAttribute(4), c.atts.getAttribute(5), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, mentalK[2], 6));	
	}
	public double getKPrecisionChar() {
		double[] attribs = {c.atts.getAttribute(3), c.atts.getAttribute(4), c.atts.getAttribute(5), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, mentalK[3], 6));	
	}
	public double getKPrecisionMan() {
		double[] attribs = {c.atts.getAttribute(3), c.atts.getAttribute(4), c.atts.getAttribute(5), c.atts.getAttribute(8), c.atts.getAttribute(7), c.atts.getAttribute(6)};
		return (Utils.dotProduct(attribs, mentalK[4], 6));	
	}

	
}
