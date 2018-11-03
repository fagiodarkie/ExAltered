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

public class ExperienceLogEntry {

	private short amount;
	private String description;
	
	public ExperienceLogEntry(short a, String d) {
		amount = a;
		description = d;
	}

	public ExperienceLogEntry() {	}

	public String toString() {
		return "" + amount + ":-:" + description;
	}
	
	public void fromString(String s) {
		if (s.equalsIgnoreCase("")) return;
		String[] r = s.trim().split(":-:");
		description = r[1];
		amount = Short.parseShort(r[0]);
	}
	
	public boolean isExpense() {
		return amount < 0;
	}
	
	public short getAmount() {
		return amount;
	}
	public void setAmount(short amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
