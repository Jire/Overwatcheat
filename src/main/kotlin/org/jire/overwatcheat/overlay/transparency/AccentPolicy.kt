/*
 * Free, open-source undetected color cheat for Overwatch!
 * Copyright (C) 2017  Thomas G. Nappo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.jire.overwatcheat.overlay.transparency

import com.sun.jna.Structure

@Structure.FieldOrder(value = ["AccentState", "AccentFlags", "GradientColor", "AnimationId"])
class AccentPolicy : Structure(), Structure.ByReference {
    @JvmField
    internal var AccentState: Int = 0

    var accentState
        get() = AccentStates[AccentState]
        set(value) {
            AccentState = value.ordinal
        }

    @JvmField
    var AccentFlags: Int = 0

    @JvmField
    var GradientColor: Int = 0

    @JvmField
    var AnimationId: Int = 0
}