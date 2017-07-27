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

public class Attributes {
	
	public static final int
		STRENGTH = 0, DEXTERITY = 1, STAMINA = 2,
		CHARISMA = 3, MANIPULATION = 4, APPEARENCE = 5,
		INTELLIGENCE = 6, PERCEPTION = 7, WITS = 8;

	private PlayCharacter c;
	private short[] attributes;
	
	
	public Attributes(PlayCharacter ch) {
		attributes = new short[9];
		for (short i = 0; i < 9; ++i) {
			attributes[i] = 0;
		}
		c = ch;
	}
	
	public Attributes(PlayCharacter ch, short[] attrs) {
		if (attrs.length == 9)
			attributes = attrs;
		c = ch;
	}
	
	public String toString() {
		return Sequences.toString(attributes);
	}
	
	public void fromString(String s) {
		attributes = Sequences.shortArrayFromString(s, ", ");
	}
				
	public short[] getAsArray() {
		return attributes;
	}
	public short getAttribute(int index) {
		return (short) (attributes[index] + c.effects.getBonus(index));
	}

	public void setAttribute(int i, short p) {
		if (i < 9) attributes[i] = p;
	}
	
}
