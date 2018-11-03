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

package it.fagio.exaltered.model.modifier.concrete;

import it.fagio.exaltered.model.arkana.EffectType;
import it.fagio.exaltered.model.modifier.Modifier;
import it.fagio.exaltered.model.modifier.Operators;

import java.util.function.BinaryOperator;

public class DiceBonus implements Comparable<DiceBonus>, Modifier {
    public boolean isInternal;
    public EffectType type;
    private Object key;
    public Double value;

    private BinaryOperator<Double> operation;

    public DiceBonus(Object key, EffectType type, boolean isInternal, Double value, BinaryOperator<Double> operation)
    {
        this.key = key;
        this.type = type;
        this.isInternal = isInternal;
        this.value = value;
        this.operation = operation;
    }

    @Override
    public Object getKey() {
        return key;
    }

    public Double apply(Double originalValue)
    {
        return operation.apply(originalValue, value);
    }


    @Override
    public int compareTo(DiceBonus o) {
        return Operators.compareMultiplicativeFirst(operation, o.operation);
    }
}
