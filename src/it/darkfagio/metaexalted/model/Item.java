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

package it.darkfagio.metaexalted.model;

import it.darkfagio.utils.Sequences;

public class Item {
		
	protected float[] weight;
	protected String name;
	protected String description;
	protected short essenceCost;
	protected boolean attuned;
	protected TempEffect[] effects;
	protected TempEffect[] attunedEffects;
	
	protected static final short usedFields = 7;
	
	public Item() {
		float[] weightz = {0, 0};
		weight = weightz;
		name = description = "";
		essenceCost = 0;
		attuned = false;
		effects = new TempEffect[0];
		attunedEffects = new TempEffect[0];
	}

	public static Item factoryFromString(String string) {
		Item i = new Item();
		i.fromString(string);
		return i;
	}
	
	public void setEffects(TempEffect[] e) {
		effects = e;
	}
	public void setAttunedEffects(TempEffect[] e) {
		attunedEffects = e;
	}
	
	public void fromString(String string) {
		String[] res = string.split(":-:");
		name = res[0].trim();
		weight = Sequences.parseFloatFromString(res[1], ",__,");
		description = res[2].trim();
		essenceCost = Short.parseShort(res[3]);
		attuned = Boolean.parseBoolean(res[4]);
		String[] r = Sequences.divideStringPer(res[5], ",_,");
		effects = new TempEffect[r.length];
		for(int i = 0; i < r.length; ++i)
			effects[i] = TempEffect.factoryFromString(r[i]);
		String[] r2 = Sequences.divideStringPer(res[6], ",_,");
		attunedEffects = new TempEffect[r2.length];
		for(int i = 0; i < r2.length; ++i)
			attunedEffects[i] = TempEffect.factoryFromString(r2[i]);
	}
	
	@Override
	public String toString() {
		return name + ":-:" + Sequences.toString(weight, ",__,") + ":-:" + description + ":-:" + essenceCost + ":-:"
				+ attuned + ":-:" + Sequences.toString(effects, ",_,") + ":-:" + Sequences.toString(attunedEffects, ",_,");
	}
	
	public TempEffect[] getEffects() {
		return effects;
	}
	
	public void addEffect(TempEffect e) {
		TempEffect[] temp = new TempEffect[effects.length + 1];
		for (int i = 0; i < effects.length; ++i)
			temp[i] = effects[i];
		temp[effects.length] = e;
		effects = temp;
	}
	
	public TempEffect getEffect(int i) {
		return effects[i];
	}
	public void removeEffect(int x) {
		TempEffect[] temp = new TempEffect[effects.length - 1];
		for (int i = 0; i < effects.length; ++i)
			if (i < x) temp[i] = effects[i];
			else if (i > x) temp[i - 1] = effects[i];
		effects = temp;
	}
	
	public TempEffect[] getAttunedEffects() {
		return attunedEffects;
	}
	
	public void addAttunedEffect(TempEffect e) {
		TempEffect[] temp = new TempEffect[attunedEffects.length + 1];
		for (int i = 0; i < attunedEffects.length; ++i)
			temp[i] = attunedEffects[i];
		temp[attunedEffects.length] = e;
		attunedEffects = temp;
	}
	
	public TempEffect getAttunedEffect(int i) {
		return attunedEffects[i];
	}
	public void removeAttunedEffect(int x) {
		TempEffect[] temp = new TempEffect[attunedEffects.length - 1];
		for (int i = 0; i < attunedEffects.length; ++i)
			if (i < x) temp[i] = attunedEffects[i];
			else if (i > x) temp[i - 1] = attunedEffects[i];
		attunedEffects = temp;
	}

	public float getWeight() {
		return (attuned ? weight[1] : weight[0]);
	}
	public float[] getWeightArray() {
		return weight;
	}
	

	public void setWeight(float[] weight) {
		this.weight = weight;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isArmonized() {
		return attuned;
	}
	public void attune() {
		if (essenceCost != 0)
		attuned = true;
	}
	public void disattune() {
		if (attuned)
		attuned = false;
	}
	
	public short getEssence() {
		return essenceCost;
	}
	public void setEssenceCost(short x) {
		essenceCost = x;
	}

	public boolean equalsTo(Item weapon) {
		if (!name.equalsIgnoreCase(weapon.name)) return false;
		if (essenceCost != weapon.essenceCost) return false;
		if (!description.equalsIgnoreCase(weapon.description)) return false;
		if ((weight[0] != weapon.weight[0]) || (weight[1] != weapon.weight[1])) return false;
		if ((effects.length != weapon.effects.length) || (attunedEffects.length != weapon.attunedEffects.length))
			return false;
		for (int i = 0; i < effects.length; ++i) if (effects[i] != weapon.effects[i]) return false;
		for (int i = 0; i < attunedEffects.length; ++i)
			if (attunedEffects[i] != weapon.attunedEffects[i]) return false;
		return true;
	}

}
