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

package com.pixelaimbot.util

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.platform.win32.WinDef

object User32 {

	init {
		Native.register("user32")
	}
	
	@JvmStatic
	external fun GetKeyState(nVirtKey: Int): Short
	
	@JvmStatic
	external fun mouse_event(dwFlags: Int, dx: Int, dy: Int, dwData: Int, dwExtraInfo: Long)
	

	@JvmStatic
	external fun GetClientRect(hWnd: Pointer, rect: Pointer): Int
	
	@JvmStatic
	external fun GetCursorPos(p: Pointer): Int
	
	@JvmStatic
	external fun FindWindowA(lpClassName: String?, lpWindowName: String): WinDef.HWND
	
	@JvmStatic
	external fun GetForegroundWindow(): Long
	
	@JvmStatic
	external fun GetWindowRect(hWnd: Pointer, rect: Pointer): Int
	
}