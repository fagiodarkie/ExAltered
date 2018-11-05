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

package it.apteroscode.exaltered.core.model.pool;

public class Health {
	private Double maxHealth, damage;

	public Health(double maxHealth)
	{
		updateMaxAndCurrent(maxHealth, 0);
	}

	public Health(double maxHealth, double damage)
	{
		updateMaxAndCurrent(maxHealth, damage);
	}

	public void updateMaxAndCurrent(double maxHealth, double damage)
	{
		this.maxHealth = maxHealth;
		this.damage = damage;
	}

	public void damage(double amount)
	{
		damage += amount;
		if (damage > maxHealth)
			damage = maxHealth;
	}

	public void cure(double amount)
	{
		damage -= amount;
		if (damage < 0D)
			damage = 0D;
	}

	public Double getCurrentHealth() {
		return maxHealth - damage;
	}

	public Double getMaxHealth() {
		return maxHealth;
	}
}
