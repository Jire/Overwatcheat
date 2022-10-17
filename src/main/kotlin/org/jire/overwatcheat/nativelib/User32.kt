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

import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinUser
import com.sun.jna.ptr.IntByReference
import com.sun.jna.win32.StdCallLibrary
import org.jire.overwatcheat.overlay.transparency.WindowCompositionAttributeData

object User32 : DirectNativeLib("user32") {

    external fun GetKeyState(nVirtKey: Int): Short
    external fun mouse_event(dwFlags: Int, dx: Int, dy: Int, dwData: Int, dwExtraInfo: Long)
    external fun SetWindowDisplayAffinity(window: Pointer, affinity: Int): Boolean
    external fun EnumWindows(lpEnumFunc: WinUser.WNDENUMPROC, userData: Pointer?): Boolean
    external fun GetWindowTextA(hWnd: Pointer, lpString: ByteArray, nMaxCount: Int): Int
    external fun GetWindow(hWnd: Pointer, uCmd: Int): Pointer

    interface WndEnumProc : StdCallLibrary.StdCallCallback {
        fun callback(hwnd: Long): Boolean
    }

    external fun SetWindowCompositionAttribute(hwnd: Long, data: WindowCompositionAttributeData): Long
    external fun SetWindowDisplayAffinity(hwnd: Long, dwAffinity: Long): Boolean
    external fun SetActiveWindow(hwnd: Long): Long
    external fun FindWindowA(s: String?, s1: String?): Long
    external fun GetWindowLongA(hwnd: Long, i: Int): Int
    external fun SetWindowLongA(hwnd: Long, i: Int, i1: Int): Int
    external fun SetWindowPos(hwnd: Long, hwnd1: Long, i: Int, i1: Int, i2: Int, i3: Int, i4: Int): Boolean
    external fun SetForegroundWindow(hwnd: Long): Boolean
    external fun SetFocus(hwnd: Long): Long
    external fun IsWindowVisible(hwnd: Long): Boolean
    external fun ShowWindow(hwnd: Long, i: Int): Boolean
    external fun GetWindowThreadProcessId(hwnd: Long, intByReference: IntByReference?): Int
    external fun AttachThreadInput(dword: Long, dword1: Long, b: Boolean): Boolean
    external fun EnumWindows(enumProc: WndEnumProc): Boolean

}