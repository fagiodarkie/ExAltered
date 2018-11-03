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

package it.fagio.exaltered.model.modifier;

import java.util.function.BinaryOperator;

public class Operators {

    public static BinaryOperator<Double> ADDITIVE = (value, modifier) -> value + modifier;

    public static BinaryOperator<Double> MULTIPLICATIVE = (value, modifier) -> value * modifier;

    public static int compareMultiplicativeFirst(BinaryOperator<Double> first, BinaryOperator<Double> second)
    {
        if ((first == Operators.ADDITIVE) && (second == Operators.MULTIPLICATIVE))
        {
            return -1;
        }

        if ((second == Operators.ADDITIVE) && (first == Operators.MULTIPLICATIVE))
        {
            return 1;
        }

        return 0;

    }
}
