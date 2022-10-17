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

package org.jire.overwatcheat.nativelib.interception

import com.sun.jna.Structure
import com.sun.jna.Structure.FieldOrder

@FieldOrder("code", "state", "flags", "rolling", "x", "y", "information")
class InterceptionStroke(
    @JvmField var code: Short = 0,
    @JvmField var state: Short = 0,
    @JvmField var information: Short = 0,
    @JvmField var rolling: Short = 0,
    @JvmField var x: Int = 0,
    @JvmField var y: Int = 0,
    @JvmField var flags: Short = 0,
    var isInjected: Boolean = false
) : Structure()