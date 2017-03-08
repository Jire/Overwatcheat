package com.overwatcheat.util

import com.sun.jna.Native

object User32 {
	
	@JvmStatic
	external fun GetKeyState(nVirtKey: Int): Short
	
	@JvmStatic
	external fun mouse_event(dwFlags: Int, dx: Int, dy: Int, dwData: Int, dwExtraInfo: Long)
	
	init {
		Native.register("user32")
	}
	
}