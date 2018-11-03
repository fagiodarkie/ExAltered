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

package it.apteroscode.exaltered.core.model.modifier.concrete;

import it.apteroscode.exaltered.core.model.arkana.EffectType;
import it.apteroscode.exaltered.core.model.modifier.Modifier;
import it.apteroscode.exaltered.core.model.modifier.Operators;

import java.util.function.BinaryOperator;

public class ValueBonus implements Comparable<ValueBonus>, Modifier {

    private Object key;
    public EffectType type;
    public Double value;
    private BinaryOperator<Double> operator;

    public ValueBonus(Object typeOfBonus, Double bonusValue, EffectType type, BinaryOperator<Double> operation)
    {
        this.key = typeOfBonus;
        this.value = bonusValue;
        this.type = type;
        this.operator = operation;
    }

    @Override
    public Object getKey() {
        return key;
    }

    public Double apply(Double originalValue)
    {
        return operator.apply(originalValue, value);
    }

    @Override
    public int compareTo(ValueBonus o) {
        return Operators.compareMultiplicativeFirst(operator, o.operator);
    }
}
