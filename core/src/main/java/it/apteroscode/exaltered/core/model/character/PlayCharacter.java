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


package it.apteroscode.exaltered.core.model.character;

import it.apteroscode.exaltered.core.model.character.ability.AbilitySet;
import it.apteroscode.exaltered.core.model.character.attribute.AttributeSet;
import it.apteroscode.exaltered.core.model.experience.ExperienceManagement;


public class PlayCharacter {

	public AttributeSet attributes;
	public AbilitySet abilities;
	public ExperienceManagement experience;

}
