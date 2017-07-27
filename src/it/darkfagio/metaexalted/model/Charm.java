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

public class Charm {
	
	private String name;
	private String description;
	public static final int
		ability = 0,
		minAbility = 1,
		minEssence = 2,
		essenceCost = 3,
		willpowerCost = 4,
		healthCost = 5,
		healthLevel = 6,
		limitCost = 7;
	
	private short[] feature;
	
	//TODO implementare i prodigi e come si interfacciano con il PG
	
	public Charm () {
		feature = new short[8];
	}

	public void fromString(String s) {
	}
	
	public static Charm factoryFromString(String s) {
		Charm c = new Charm();
		c.fromString(s);
		return c;
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

	public short get(int position) {
		return feature[position];
	}
	
	public void set(int index, short val) {
		feature[index] = val;
	}

}
