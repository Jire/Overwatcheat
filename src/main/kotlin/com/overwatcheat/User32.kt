package com.overwatcheat

import com.sun.jna.Native

object User32 {
	
	@JvmStatic
	external fun GetKeyState(nVirtKey: Int): Short
	
	@JvmStatic
	external fun mouse_event(dwFlags: Int, dx: Int, dy: Int, dwData: Int, dwExtraInfo: Long)
	
	@JvmStatic
	external fun keybd_event(bVk: Byte, bScan: Byte, dwFlags: Int, dwExtraInfo: Int): Long
	
	init {
		Native.register("user32")
	}
	
}