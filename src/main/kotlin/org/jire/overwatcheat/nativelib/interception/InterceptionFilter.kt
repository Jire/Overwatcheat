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

object InterceptionFilter {

    const val INTERCEPTION_KEY_DOWN = 0x00
    const val INTERCEPTION_KEY_UP = 0x01
    const val INTERCEPTION_KEY_E0 = 0x02
    const val INTERCEPTION_KEY_E1 = 0x04
    const val INTERCEPTION_KEY_TERMSRV_SET_LED = 0x08
    const val INTERCEPTION_KEY_TERMSRV_SHADOW = 0x10
    const val INTERCEPTION_KEY_TERMSRV_VKPACKET = 0x20
    const val INTERCEPTION_FILTER_KEY_NONE = 0x0000
    const val INTERCEPTION_FILTER_KEY_ALL = 0xFFFF
    const val INTERCEPTION_FILTER_KEY_DOWN = INTERCEPTION_KEY_UP
    const val INTERCEPTION_FILTER_KEY_UP = INTERCEPTION_KEY_UP shl 1
    const val INTERCEPTION_FILTER_KEY_E0 = INTERCEPTION_KEY_E0 shl 1
    const val INTERCEPTION_FILTER_KEY_E1 = INTERCEPTION_KEY_E1 shl 1
    const val INTERCEPTION_FILTER_KEY_TERMSRV_SET_LED = INTERCEPTION_KEY_TERMSRV_SET_LED shl 1
    const val INTERCEPTION_FILTER_KEY_TERMSRV_SHADOW = INTERCEPTION_KEY_TERMSRV_SHADOW shl 1
    const val INTERCEPTION_FILTER_KEY_TERMSRV_VKPACKET = INTERCEPTION_KEY_TERMSRV_VKPACKET shl 1

    const val INTERCEPTION_MOUSE_LEFT_BUTTON_DOWN = 0x001
    const val INTERCEPTION_MOUSE_LEFT_BUTTON_UP = 0x002
    const val INTERCEPTION_MOUSE_RIGHT_BUTTON_DOWN = 0x004
    const val INTERCEPTION_MOUSE_RIGHT_BUTTON_UP = 0x008
    const val INTERCEPTION_MOUSE_MIDDLE_BUTTON_DOWN = 0x010
    const val INTERCEPTION_MOUSE_MIDDLE_BUTTON_UP = 0x020

    const val INTERCEPTION_MOUSE_BUTTON_1_DOWN = INTERCEPTION_MOUSE_LEFT_BUTTON_DOWN
    const val INTERCEPTION_MOUSE_BUTTON_1_UP = INTERCEPTION_MOUSE_LEFT_BUTTON_UP
    const val INTERCEPTION_MOUSE_BUTTON_2_DOWN = INTERCEPTION_MOUSE_RIGHT_BUTTON_DOWN
    const val INTERCEPTION_MOUSE_BUTTON_2_UP = INTERCEPTION_MOUSE_RIGHT_BUTTON_UP
    const val INTERCEPTION_MOUSE_BUTTON_3_DOWN = INTERCEPTION_MOUSE_MIDDLE_BUTTON_DOWN
    const val INTERCEPTION_MOUSE_BUTTON_3_UP = INTERCEPTION_MOUSE_MIDDLE_BUTTON_UP

    const val INTERCEPTION_FILTER_MOUSE_NONE = 0x0000
    const val INTERCEPTION_FILTER_MOUSE_ALL = 0xFFFF

    const val INTERCEPTION_FILTER_MOUSE_LEFT_BUTTON_DOWN = INTERCEPTION_MOUSE_LEFT_BUTTON_DOWN
    const val INTERCEPTION_FILTER_MOUSE_LEFT_BUTTON_UP = INTERCEPTION_MOUSE_LEFT_BUTTON_UP
    const val INTERCEPTION_FILTER_MOUSE_RIGHT_BUTTON_DOWN = INTERCEPTION_MOUSE_RIGHT_BUTTON_DOWN
    const val INTERCEPTION_FILTER_MOUSE_RIGHT_BUTTON_UP = INTERCEPTION_MOUSE_RIGHT_BUTTON_UP
    const val INTERCEPTION_FILTER_MOUSE_MIDDLE_BUTTON_DOWN = INTERCEPTION_MOUSE_MIDDLE_BUTTON_DOWN
    const val INTERCEPTION_FILTER_MOUSE_MIDDLE_BUTTON_UP = INTERCEPTION_MOUSE_MIDDLE_BUTTON_UP

    const val INTERCEPTION_FILTER_MOUSE_BUTTON_1_DOWN = INTERCEPTION_MOUSE_BUTTON_1_DOWN
    const val INTERCEPTION_FILTER_MOUSE_BUTTON_1_UP = INTERCEPTION_MOUSE_BUTTON_1_UP
    const val INTERCEPTION_FILTER_MOUSE_BUTTON_2_DOWN = INTERCEPTION_MOUSE_BUTTON_2_DOWN
    const val INTERCEPTION_FILTER_MOUSE_BUTTON_2_UP = INTERCEPTION_MOUSE_BUTTON_2_UP
    const val INTERCEPTION_FILTER_MOUSE_BUTTON_3_DOWN = INTERCEPTION_MOUSE_BUTTON_3_DOWN
    const val INTERCEPTION_FILTER_MOUSE_BUTTON_3_UP = INTERCEPTION_MOUSE_BUTTON_3_UP

    const val INTERCEPTION_FILTER_MOUSE_MOVE = 0x1000

}