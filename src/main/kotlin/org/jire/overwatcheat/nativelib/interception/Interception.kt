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

import com.sun.jna.Callback
import com.sun.jna.Pointer
import org.jire.overwatcheat.nativelib.DirectNativeLib

object Interception : DirectNativeLib("interception") {

    @JvmStatic
    external fun interception_is_keyboard(device: Int): Int

    @JvmStatic
    external fun interception_is_mouse(device: Int): Int

    @JvmStatic
    external fun interception_create_context(): Pointer

    @JvmStatic
    external fun interception_set_filter(context: Pointer, predicate: Callback, filter: Short)

    @JvmStatic
    external fun interception_receive(context: Pointer, device: Int, stroke: InterceptionStroke, nstroke: Int): Int

    @JvmStatic
    external fun interception_wait(context: Pointer): Int

    @JvmStatic
    external fun interception_send(context: Pointer, device: Int, stroke: InterceptionStroke, nstroke: Int): Int

    @JvmStatic
    external fun interception_destroy_context(context: Pointer)

}