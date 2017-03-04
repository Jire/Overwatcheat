package com.overwatcheat

import com.sun.jna.Native
import com.sun.jna.platform.win32.WinDef

object User32 {
	
	@JvmStatic
	external fun GetKeyState(nVirtKey: Int): Short
	
	@JvmStatic
	external fun GetCursorPos(p: WinDef.POINT): Boolean
	
	@JvmStatic
	external fun mouse_event(dwFlags: Int, dx: Int, dy: Int, dwData: Int, dwExtraInfo: Long)
	
	init {
		Native.register("user32")
	}
	
}