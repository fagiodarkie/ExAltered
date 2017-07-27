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

package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

import java.util.ArrayList;
import java.util.List;


public class PlayCharacter {

	public Attributes atts;
	public Abilities abils;
	public Equipment equip;
	public String name;
	public Pools pools;
	public HealthManager health;
	public List<Charm> charms;
	public List<Background> background;
	public Calculator calc;
	public EffectManager effects;
	public List<Movement> movements;
	
	public static String SEPARATOR = "/---/";
	
	public PlayCharacter() {
		atts = new Attributes(this);
		abils = new Abilities(this);
		equip = new Equipment(this);
		charms = new ArrayList<Charm>();
		background = new ArrayList<Background>();
		pools = new Pools(this);
		health = new HealthManager();
		movements = new ArrayList<Movement>();
		name = "";
		effects = new EffectManager(this);
		calc = new Calculator(this);
	}
	
	@Override
	public String toString() {
		String repr = name + SEPARATOR + atts.toString()
				+ SEPARATOR + abils.toString() + SEPARATOR + equip.toString()
				+ SEPARATOR + health.toString()	+ SEPARATOR + pools.toString()
				+ SEPARATOR + Sequences.toString(charms) + SEPARATOR + Sequences.toString(background)
				+ SEPARATOR + effects.toString() + SEPARATOR + Sequences.toString(movements);
		return repr;
	}
	
	public void fromString(String s) {
		String[] res = s.split(SEPARATOR);
		name = res[0].trim();
		atts.fromString(res[1]);
		abils.fromString(res[2]);
		equip.fromString(res[3]);
		health.fromString(res[4]);
		pools.fromString(res[5]);
		String[] chs = Sequences.divideStringPer(res[6], ", ");
		String[] bgs = Sequences.divideStringPer(res[7], ", ");
		effects.fromString(res[8]);
		String[] mvms = Sequences.divideStringPer(res[9], ", ");
		for (int i = 0; i < chs.length; ++i) {
			charms.add(Charm.factoryFromString(chs[i]));
		}
		for (int i = 0; i < bgs.length; ++i) {
			background.add(Background.factoryFromString(bgs[i]));
		}
		for (int i = 0; i < mvms.length; ++i) {
			movements.add(Movement.factoryFromString(mvms[i]));
		}
	}
	
	public Attributes getAtts() {
		return atts;
	}
	public void setAtts(Attributes atts) {
		this.atts = atts;
	}
	public Abilities getAbils() {
		return abils;
	}
	public void setAbils(Abilities abils) {
		this.abils = abils;
	}
	public Equipment getEquip() {
		return equip;
	}
	public void setEquip(Equipment equip) {
		this.equip = equip;
	}
	public List<Charm> getCharms() {
		return charms;
	}
	public void setCharms(List<Charm> charms) {
		this.charms = charms;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Pools getPools() {
		return pools;
	}
	public void setPools(Pools pools) {
		this.pools = pools;
	}
	public HealthManager getHealth() {
		return health;
	}
	public void setHealth(HealthManager health) {
		this.health = health;
	}

}
