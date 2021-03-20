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

import com.overwatcheat.Overwatcheat
import com.overwatcheat.nativelib.Kernel32
import com.sun.jna.Callback

object Mouse : Thread() {

    private val mouseCallback = object : Callback {
        fun callback(device: Int) = Interception.interception_is_mouse(device)
    }
    private val keyboardCallback = object : Callback {
        fun callback(device: Int) = Interception.interception_is_keyboard(device)
    }
    private val context = Interception.interception_create_context()
    private val emptyStroke = InterceptionStroke()

    override fun run() {
        var device: Int
        while (Interception.interception_receive(
                context,
                Interception.interception_wait(context).also { device = it },
                emptyStroke,
                1
            ) > 0
        ) {
            if (!emptyStroke.isInjected) {
                Interception.interception_send(context, device, emptyStroke, 1)
            }
        }
        Interception.interception_destroy_context(context)
    }

    val stroke = InterceptionStroke(
        0, 0,
        (InterceptionMouseFlag.INTERCEPTION_MOUSE_MOVE_RELATIVE or
                InterceptionMouseFlag.INTERCEPTION_MOUSE_CUSTOM).toShort(),
        0, 0, 0, 0, true
    )

    fun move(x: Int, y: Int) {
        stroke.x = x
        stroke.y = y
        Interception.interception_send(context, Overwatcheat.SETTINGS.deviceId, stroke, 1)
    }

    init {
        Kernel32.SetPriorityClass(Kernel32.GetCurrentProcess(), Kernel32.HIGH_PRIORITY_CLASS)
        Interception.interception_set_filter(
            context,
            mouseCallback,
            InterceptionFilter.INTERCEPTION_FILTER_MOUSE_MOVE.toShort()
        )
        start()
    }

}