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

import it.apteroscode.exaltered.core.model.modifier.Modifier;

import java.util.List;
import java.util.Map;

public class Weapon implements Item {

	private boolean active;
	private Double weight;

	public void enable()
	{
		active = true;
	}

	@Override
	public Double getWeigth() {
		return weight;
	}

	@Override
	public int getPrice() {
		return 0;
	}

	@Override
	public Map<Object, Double> getMinimalRequirements() {
		return null;
	}

	@Override
	public List<Modifier> getEffects() {
		return null;
	}

	@Override
	public List<Modifier> getActiveEffects() {
		return null;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public boolean isAttuned() {
		return false;
	}

	@Override
	public Double getHarmonizationCost() {
		return null;
	}

	@Override
	public Object getHarmonizationPool() {
		return null;
	}
}
