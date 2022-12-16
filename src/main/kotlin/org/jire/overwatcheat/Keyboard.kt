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

package org.jire.overwatcheat

import org.jire.overwatcheat.nativelib.User32Panama
import org.jire.overwatcheat.nativelib.interception.InterceptionKeyState
import org.jire.overwatcheat.nativelib.interception.InterceptionPanama.context
import org.jire.overwatcheat.nativelib.interception.InterceptionPanama.interception_send
import java.lang.foreign.MemorySegment
import java.lang.foreign.MemorySession
import java.lang.foreign.ValueLayout

object Keyboard {

    fun keyState(virtualKeyCode: Int) = User32Panama.GetKeyState(virtualKeyCode)

    fun keyPressed(virtualKeyCode: Int) = keyState(virtualKeyCode) < 0

    fun keyReleased(virtualKeyCode: Int) = !keyPressed(virtualKeyCode)

    val keyStroke =
        MemorySegment.allocateNative(18, 4, MemorySession.global()).apply {
            set(ValueLayout.JAVA_SHORT, 0, 0) // code
            set(ValueLayout.JAVA_SHORT, 2, 0) // state
            set(ValueLayout.JAVA_INT, 4, 0) // information
        }

    fun pressKey(key: Int, deviceId: Int) {
        keyStroke.run {
            set(ValueLayout.JAVA_SHORT, 0, key.toShort())
            set(ValueLayout.JAVA_SHORT, 2, InterceptionKeyState.INTERCEPTION_KEY_DOWN.toShort())
        }
        interception_send(context, deviceId, keyStroke, 1)
    }

    fun releaseKey(key: Int, deviceId: Int) {
        keyStroke.run {
            set(ValueLayout.JAVA_SHORT, 0, key.toShort())
            set(ValueLayout.JAVA_SHORT, 2, InterceptionKeyState.INTERCEPTION_KEY_UP.toShort())
        }
        interception_send(context, deviceId, keyStroke, 1)
    }

}

