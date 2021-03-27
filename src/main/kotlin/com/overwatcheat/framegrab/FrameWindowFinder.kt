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

package com.overwatcheat.framegrab

import com.overwatcheat.nativelib.User32
import com.sun.jna.Native
import com.sun.jna.Pointer

object FrameWindowFinder {

    private const val WINDOW_TITLE_BYTES_SIZE = 512

    private var windowTitle: CharSequence? = null

    fun findWindowTitle(windowTitleSearch: CharSequence): CharSequence {
        User32.EnumWindows({ hwnd, _ ->
            val windowTitleBytes = ByteArray(WINDOW_TITLE_BYTES_SIZE)
            User32.GetWindowTextA(hwnd.pointer, windowTitleBytes, windowTitleBytes.size)
            val windowTitle = Native.toString(windowTitleBytes).trim { it <= ' ' }
            if (windowTitle.contains(windowTitleSearch)) {
                FrameWindowFinder.windowTitle = windowTitle
            }
            true
        }, Pointer.NULL)

        if (windowTitle == null) {
            throw Exception("Could not find window title with search: \"$windowTitleSearch\"")
        }
        return windowTitle!!
    }

}