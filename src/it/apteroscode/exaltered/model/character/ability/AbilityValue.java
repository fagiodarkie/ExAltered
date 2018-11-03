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

package it.apteroscode.exaltered.model.character.ability;

import java.util.HashMap;
import java.util.Map;

public class AbilityValue {

    private AbilityEnum abilityType;
    private Map<String, Integer> specializations;
    private Integer abilityValue;

    public AbilityValue(AbilityEnum abilityType)
    {
        init(abilityType, 0, null);
    }

    public AbilityValue(AbilityEnum abilityType, int abilityValue)
    {
        init(abilityType, abilityValue, null);
    }

    public AbilityValue(AbilityEnum abilityType, int abilityValue, Map<String, Integer> specializations)
    {
        init(abilityType, abilityValue, specializations);
    }

    public AbilityEnum getAbilityType() {
        return abilityType;
    }

    public int getAbilityValue() {
        return abilityValue;
    }

    public void setAbilityValue(Integer abilityValue) {
        this.abilityValue = abilityValue;
    }

    public int getSpecializationValue(String specializationName)
    {
        return specializations.containsKey(specializationName)
            ? specializations.get(specializationName)
            : 0;
    }

    public void setSpecializationValue(String specializationName, int specializationValue)
    {
        specializations.put(specializationName, specializationValue);
    }

    private void init(AbilityEnum abilityType, int abilityValue, Map<String, Integer> specializations)
    {
        this.specializations = specializations == null
            ? new HashMap<>()
            : specializations;

        this.abilityType = abilityType;
        this.abilityValue = abilityValue;

    }
}
