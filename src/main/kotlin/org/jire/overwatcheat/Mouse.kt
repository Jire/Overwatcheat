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

import org.jire.overwatcheat.nativelib.Kernel32
import org.jire.overwatcheat.nativelib.interception.InterceptionFilter
import org.jire.overwatcheat.nativelib.interception.InterceptionMouseFlag
import org.jire.overwatcheat.nativelib.interception.InterceptionPanama
import org.jire.overwatcheat.nativelib.interception.InterceptionPanama.interceptionMouseStrokeLayout
import java.lang.Thread.sleep
import java.lang.foreign.MemorySegment
import java.lang.foreign.MemorySession
import java.lang.foreign.ValueLayout

object Mouse {

    private val context = InterceptionPanama.interception_create_context()

    val stroke =
        MemorySegment.allocateNative(interceptionMouseStrokeLayout, MemorySession.global()).apply {
            set(ValueLayout.JAVA_SHORT, 0, 0) // state
            set(ValueLayout.JAVA_SHORT, 2, 0) // flags
            set(ValueLayout.JAVA_SHORT, 4, 0) // rolling
            set(ValueLayout.JAVA_INT, 8, 0) // x
            set(ValueLayout.JAVA_INT, 12, 0) // y
            set(
                ValueLayout.JAVA_SHORT, 14,
                (InterceptionMouseFlag.INTERCEPTION_MOUSE_MOVE_RELATIVE or
                        InterceptionMouseFlag.INTERCEPTION_MOUSE_CUSTOM).toShort()
            ) // information
        }

    fun move(x: Int, y: Int, deviceID: Int) {
        stroke.run {
            set(ValueLayout.JAVA_INT, 8, x)
            set(ValueLayout.JAVA_INT, 12, y)
        }
        InterceptionPanama.interception_send(context, deviceID, stroke, 1)
    }

    fun click(deviceID: Int) {
        stroke.run {
            setAtIndex(ValueLayout.JAVA_INT, 0, InterceptionFilter.INTERCEPTION_MOUSE_LEFT_BUTTON_DOWN)
            set(ValueLayout.JAVA_INT, 8, 0)
            set(ValueLayout.JAVA_INT, 12, 0)
        }
        InterceptionPanama.interception_send(context, deviceID, stroke, 1)
        sleep(300)
        stroke.setAtIndex(ValueLayout.JAVA_INT, 0, InterceptionFilter.INTERCEPTION_MOUSE_LEFT_BUTTON_UP)
        InterceptionPanama.interception_send(context, deviceID, stroke, 1)
    }

    init {
        Kernel32.SetPriorityClass(Kernel32.GetCurrentProcess(), Kernel32.HIGH_PRIORITY_CLASS)
    }

}
