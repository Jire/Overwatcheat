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

import com.sun.jna.Pointer
import com.sun.jna.Structure

@Structure.FieldOrder(value = ["Attribute", "Data", "SizeOfData"])
class WindowCompositionAttributeData() : Structure(), Structure.ByReference {

    @JvmField
    internal var Attribute: Int = 0

    private var attribute: WindowCompositionAttributes
        get() = WindowCompositionAttributes[Attribute]
        set(value) {
            Attribute = value.id
        }

    @JvmField
    var Data: Pointer? = null

    @JvmField
    var SizeOfData: Int = 0

    constructor(
        Attribute: WindowCompositionAttributes = WindowCompositionAttributes.WCA_ACCENT_POLICY,
        AccentState: AccentStates = AccentStates.ACCENT_DISABLED,
        AccentFlags: Int = 0,
        GradientColor: Int = 0,
        AnimationId: Int = 0
    ) : this() {
        val accent = AccentPolicy()
        accent.accentState = AccentState
        accent.AccentFlags = AccentFlags // must be 2 for transparency
        accent.GradientColor = GradientColor // ARGB color code for gradient
        accent.AnimationId = AnimationId
        val accentStructSize = accent.size()
        accent.write()
        val accentPtr = accent.pointer
        this.attribute = Attribute
        this.SizeOfData = accentStructSize
        this.Data = accentPtr
    }

}