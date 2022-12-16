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

object InterceptionKeyState {

    const val INTERCEPTION_KEY_DOWN = 0x00
    const val INTERCEPTION_KEY_UP = 0x01
    const val INTERCEPTION_KEY_E0 = 0x02
    const val INTERCEPTION_KEY_E1 = 0x04
    const val INTERCEPTION_KEY_TERMSRV_SET_LED = 0x08
    const val INTERCEPTION_KEY_TERMSRV_SHADOW = 0x10
    const val INTERCEPTION_KEY_TERMSRV_VKPACKET = 0x20

}
