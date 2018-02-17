package com.pixelaimbot.util.extensions

import com.pixelaimbot.util.User32
import com.sun.jna.platform.win32.WinDef
import java.lang.Math.sqrt

fun WinDef.POINT.set(x: Int, y: Int) = apply {
	this.x = x
	this.y = y
}

fun WinDef.POINT.refresh() = apply { User32.GetCursorPos(this.pointer) }

fun WinDef.POINT.distance(b: WinDef.POINT): Double {
	val px = (b.x - this.x).toDouble()
	val py = (b.y - this.y).toDouble()
	return sqrt(px * px + py * py)
}