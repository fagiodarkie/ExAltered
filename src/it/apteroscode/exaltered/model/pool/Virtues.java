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

package it.apteroscode.exaltered.model.pool;

public class Virtues {

	public static final String[] name = {"Convinzione", "Compassione", "Valore", "Temperanza"};
	public static short convinction = 0,
		compassion = 1,
		valor = 2,
		temperance = 3,
		usedConv = 4,
		usedComp = 5,
		usedVal = 6,
		usedTemp = 7,
		statNum = 8;
	
	private short[] stats;
	
	public Virtues() {
		stats = new short[statNum];
		for (int i = 0; i < statNum; ++i) stats[i] = 0;
	}

	public short getStat(short pos) {
		return stats[pos];
	}
	
	public void setStat(short pos, int val) {
		stats[pos] = (short) val;
	}
	
	public void consumeVirtue(int virt) {
		stats[virt + 4] ++;
	}
	
	public void restoreVirtue(int virt) {
		stats[virt + 4] --;
	}
	
}
