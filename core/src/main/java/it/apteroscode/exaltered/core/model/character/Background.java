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

package it.apteroscode.exaltered.core.model.character;

public class Background {

	private String name;
	private String description;
	private short degree;
	
	public Background() {
		setName("");
		setDescription("");
		setDegree((short)0);
	}
	
	public static Background factoryFromString(String s) {
		Background b = new Background();
		b.fromString(s);
		return b;
	}

	public void fromString(String s) {
		String[] res = s.split("/");
		setName(res[0]);
		setDescription(res[1]);
		setDegree(Short.parseShort(res[2]));
	}
	
	public String toString() {
		return name + "/" + description + "/" + degree;
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

	public short getDegree() {
		return degree;
	}

	public void setDegree(short degree) {
		this.degree = degree;
	}
	
}
