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

package com.overwatcheat.nativelib

import com.overwatcheat.Overwatcheat
import com.sun.jna.Native
import com.sun.jna.Pointer

object HWNDFinder {

    private lateinit var windowTitle: String

    val projectorWindowTitle by lazy(LazyThreadSafetyMode.NONE) {
        User32.EnumWindows({ hWnd, _ ->
            val windowTitleArray = ByteArray(512)
            User32.GetWindowTextA(hWnd.pointer, windowTitleArray, windowTitleArray.size)
            val windowTitle = Native.toString(windowTitleArray).trim { it <= ' ' }
            if (windowTitle.contains(Overwatcheat.SETTINGS.windowTitleSearch)) {
                HWNDFinder.windowTitle = windowTitle
            }
            true
        }, Pointer.NULL)

        return@lazy windowTitle
    }

}