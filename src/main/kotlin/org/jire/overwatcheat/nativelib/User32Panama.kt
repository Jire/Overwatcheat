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

package org.jire.overwatcheat.nativelib

import java.lang.foreign.*

object User32Panama {

    val linker = Linker.nativeLinker()
    val user32 = SymbolLookup.libraryLookup("User32", MemorySession.global())

    private val getKeyState = linker.downcallHandle(
        user32.lookup("GetKeyState").get(),
        FunctionDescriptor.of(ValueLayout.JAVA_SHORT, ValueLayout.JAVA_INT)
    )

    fun GetKeyState(nVirtKey: Int): Short = getKeyState.invokeExact(nVirtKey) as Short

    private val mapVirtualKeyA = linker.downcallHandle(
        user32.lookup("MapVirtualKeyA").get(),
        FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.JAVA_INT, ValueLayout.JAVA_INT
        ),
    )

    object VirtualKeyMapType {
        const val MAPVK_VK_TO_VSC = 0
        const val MAPVK_VSC_TO_VK = 1
        const val MAPVK_VK_TO_CHAR = 2
        const val MAPVK_VSC_TO_VK_EX = 3
        const val MAPVK_VK_TO_VSC_EX = 4
    }

    fun MapVirtualKeyA(uCode: Int, uMapType: Int = VirtualKeyMapType.MAPVK_VK_TO_VSC): Int =
        mapVirtualKeyA.invokeExact(uCode, uMapType) as Int

}
