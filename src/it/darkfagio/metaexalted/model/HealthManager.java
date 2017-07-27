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

public class HealthManager {

	public final static short
		HEAD = 0,
		BODY = 1,
		ARM_DX = 2,
		ARM_SX = 3,
		LEG_DX = 4,
		LEG_SX = 5,
		
		BASHING = 0,
		LETHAL = 1,
		AGGRAVATED = 2;
	public static final String[] NAME = {"Testa", "Tronco", "Braccio Dx", "Braccio Sx", "Gamba Dx", "Gamba Sx"};
	public static final String[] DAMAGE = {"Contundente", "Letale", "Aggravato"};
	private HealthElement[] health;
	
	
	public HealthManager() {
		health = new HealthElement[6];
		for (short i = 0; i < 6; ++i)
			health[i] = new HealthElement(i);
	}
	
	public HealthElement get(int i) {
		if ((0 <= i) && (i < 6)) return health[i];
		else return null;
	}
	
	
	public String toString() {
		String res = health[0].toString();
		for (int i = 1; i < 6; ++i) {
			res += "/" + health[i].toString();
		}
		return res;
	}


	public void fromString(String string) {
		String[] res = string.split("/");
		for (int i = 0; i < 6; ++i) {
			health[i] = new HealthElement();
			health[i].fromString(res[i].trim());
		}
	}

	public short getDVPenalty() {
		short res = 0;
		for (int i = 0; i < 6; ++i) 
			res += health[i].getPenalty();
		return res;
	}
	public short getDVPenalty(int pos) {
		return health[pos].getPenalty();
	}

	public short getDodgePenalty(int position) {
		if (position > 1) return 0;
		short res = 0;
		if (!health[LEG_DX].isIncapacitated()) res++;
		if (!health[LEG_SX].isIncapacitated()) res++;
		if ((res == 2) && !(health[ARM_DX].isIncapacitated() || health[ARM_SX].isIncapacitated()))
			res++;
		
		return res;
	}

	public short getParryPenalty(int position) {
		if (position > 1) return 0;
		short res = 0;
		if (!health[ARM_DX].isIncapacitated()) res++;
		if (!health[ARM_SX].isIncapacitated()) res++;
		if ((res == 2) && !(health[LEG_DX].isIncapacitated() || health[LEG_SX].isIncapacitated()))
			res++;
		
		return res;
	}
	
}
