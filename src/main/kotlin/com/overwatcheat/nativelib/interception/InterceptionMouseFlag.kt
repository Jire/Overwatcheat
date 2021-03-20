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

package com.overwatcheat.nativelib.interception

object InterceptionMouseFlag {

    const val INTERCEPTION_MOUSE_MOVE_RELATIVE = 0x000
    const val INTERCEPTION_MOUSE_MOVE_ABSOLUTE = 0x001
    const val INTERCEPTION_MOUSE_VIRTUAL_DESKTOP = 0x002
    const val INTERCEPTION_MOUSE_ATTRIBUTES_CHANGED = 0x004
    const val INTERCEPTION_MOUSE_MOVE_NOCOALESCE = 0x008
    const val INTERCEPTION_MOUSE_TERMSRV_SRC_SHADOW = 0x100
    const val INTERCEPTION_MOUSE_CUSTOM = 0x200

}