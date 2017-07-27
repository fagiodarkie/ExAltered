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

public class Armor extends Item {
	
	public final static short
		hindrance = 0,
		stance = 1,
		dressing = 2,
		durability = 3,
		addedStats = 4;

	private short[] stats,
		armonizedStats,
		soak,
		bodyParts;
	
	public Armor() {
		super();
		stats = new short[4];
		bodyParts = new short[0];
		setArmonizedStats(new short[4]);
		soak = new short[8];
		for(int i = 0; i < addedStats; ++i) {
			stats[i] = armonizedStats[i] = 0;
		}
		for (int i = 0; i < 8; ++i) soak[i] = 0;
	}
	
	public static Armor factoryFromString(String string) {
		Armor a = new Armor();
		a.fromString(string);
		return a;
	}
	
	@Override 
	public void fromString(String s) {
		super.fromString(s);
		String[] res = s.split(":-:"); 
		stats = Sequences.shortArrayFromString(res[Item.usedFields], ",__,");
		armonizedStats = Sequences.shortArrayFromString(res[Item.usedFields + 1], ",__,");
		soak = Sequences.shortArrayFromString(res[Item.usedFields + 2], ",__,");
		bodyParts = Sequences.shortArrayFromString(res[Item.usedFields + 3], ",__,");
	}
	
	@Override
	public String toString() {
		return super.toString() + ":-:" + Sequences.toString(stats, ",__,") + ":-:" + Sequences.toString(armonizedStats, ",__,")
				+ ":-:" + Sequences.toString(soak, ",__,") + ":-:" + Sequences.toString(bodyParts, ",__,");
	}

	public short[] getStats() {
		return stats;
	}
	public short getStat(short i) {
		return (short) (stats[i]);
	}
	
	public short[] getBodyParts() {
		return bodyParts;
	}
	public short getBodyPart(int i) {
		return bodyParts[i];
	}
	public void setBodyPart(short[] p) {
		bodyParts = p;
	}
	
	public void addBodyPart(short part) {
		short[] tmp = new short[bodyParts.length + 1];
		for (int i = 0; i < bodyParts.length; ++i)
			tmp[i] = bodyParts[i];
		tmp[bodyParts.length] = part;
		bodyParts = tmp;
	}
	public void removeBodyPart(int index) {
		short[] tmp = new short[bodyParts.length - 1];
		for (int i = 0; i < bodyParts.length - 1; ++i)
			if (i < index) tmp[i] = bodyParts[i];
			else tmp[i] = bodyParts[i + 1];
		bodyParts = tmp;
	}

	public void setNormalStat(short index, short stats) {
		this.stats[index] = stats;
	}
	public void setNormalStats(short[] stats) {
		this.stats = stats;
	}
	
	public short[] getStat() {
		if (attuned) return armonizedStats;
		return stats;
	}
	
	public short getStat(int i) {
		return getStat()[i];
	}
	
	public short[] getArmonizedStats() {
		return armonizedStats;
	}
	public short getArmonizedStats(int i) {
		return armonizedStats[i];
	}

	public void setArmonizedStats(short[] armonizedStats) {
		this.armonizedStats = armonizedStats;
	}
	
	public void setArmonizedStats(int index, short armonizedStats) {
		this.armonizedStats[index] = armonizedStats;
	}
	
	public short[] getPhysicalSoak() {
		short[] x1 = {soak[0], soak[1], soak[2]},
				x2 = {soak[4], soak[5], soak[6]};
		if (attuned) return x2;
		return x1;
	}
	
	public short getMentalSoak() {
		if (!attuned) return soak[3];
		return soak[7];
	}
	
	public short[] getNormalSoak() {
		short[] x1 = {soak[0], soak[1], soak[2], soak[3]};
		return x1;
	}
	public short[] getAttunedSoak() {
		short[] x1 = {soak[4], soak[5], soak[6], soak[7]};
		return x1;
	}
	public short getNormalSoak(int i) {
		return getNormalSoak()[i];
	}
	public short getAttunedSoak(int i) {
		return getAttunedSoak()[i];
	}
	public short getSoak(int i) {
		if ((i % 4) < 3) return getPhysicalSoak()[i % 4];
		return getMentalSoak();
	}

	public short[] getSoak() {
		if (!attuned) return getNormalSoak();
		return getAttunedSoak();
	}

	public void setNormalSoak(short[] bonus) {
		for (int i = 0; i < 4; ++i)
			soak[i] = bonus[i];
	}
	public void setAttunedSoak(short[] bonus) {
		for (int i = 0; i < 4; ++i)
			soak[i + 4] = bonus[i];
	}

}
