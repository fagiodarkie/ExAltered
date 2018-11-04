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

package it.apteroscode.exaltered.core.model.equipment;

import it.apteroscode.exaltered.core.model.modifier.TempEffect;

public class Minimum {
	
	// minimum stats are chosen from: * attributes * abilities * pool stats.
	private short stat;
	private short value;
	
	public Minimum(short s, short v) {
		setStat(s);
		setValue(v);
	}
	
	public Minimum(String s) {
		fromString(s);
	}
	
	public Minimum() {
		value = stat = 0;
	}

	public short getPenalty(short statVal) {
		if (statVal >= value) return 0;
		return (short) (statVal - value);
	}
	
	public static int convertListIndexToStat(int x) {
		if (x > 8) x++;
		if (x > 34) x += 5;
		return x;
	}
	public static int convertStatToListIndex(int x) {
		if (x > 8) x--;
		if (x > 33) x -= 5;
		return x;
	}
	
	public String getStringPreview() {
		String s = TempEffect.getBonusName(stat);
				
		return s + ": " + value;
	}
	
	public boolean equals(Minimum m) {
		return (stat == m.stat) && (value == m.value);
	}
	
	public void fromString(String s) {
		String[] r = s.split("\\.");
		setStat(Short.parseShort(r[0]));
		setValue(Short.parseShort(r[1]));
	}
	
	public String toString() {
		return getStat() + "." + getValue();
	}
	
	public static Minimum factoryFromString(String s) {
		Minimum m = new Minimum(s);
		return m;
	}

	public short getValue() {
		return value;
	}

	public void setValue(short value) {
		this.value = value;
	}

	public short getStat() {
		return stat;
	}

	public void setStat(short stat) {
		this.stat = stat;
	}

}