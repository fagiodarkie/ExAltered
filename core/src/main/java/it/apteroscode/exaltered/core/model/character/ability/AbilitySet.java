/*
 *
 * Author: Jacopo Freddi
 * Project: ExAltered
 * Description: Interactive Character Sheet for a heavily-altered version of
 *   the  role-playing game Exalted, 2nd Edition, by White Wolf Publishing.
 *
 * Copyright (C) 2014 - 2017 Jacopo Freddi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * (C) 2017 White Wolf Entertainment AB. All rights reserved.
 * Exalted(C) and Storytelling System(TM) are trademarks and/or
 * registered trademarks of White Wolf Entertainment AB.
 *
 * All rights reserved. www.white-wolf.com
 * /
 */

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

package it.apteroscode.exaltered.core.model.character.ability;

import it.apteroscode.exaltered.core.model.character.PlayCharacter;

import java.util.HashMap;
import java.util.Map;

public class AbilitySet {

	private PlayCharacter c;

	private Map<AbilityEnum, AbilityValue> abilities;
	
	public AbilitySet(PlayCharacter ch) {
		c = ch;
		abilities = new HashMap<>();
		for (AbilityEnum ability : AbilityEnum.values())
        {
            abilities.put(ability, new AbilityValue(ability));
        }
	}

	public AbilityValue getAbility(AbilityEnum ability)
    {
        return abilities.get(ability);
    }
}
