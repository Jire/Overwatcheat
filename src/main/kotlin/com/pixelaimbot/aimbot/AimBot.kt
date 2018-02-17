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

package com.pixelaimbot.aimbot

import com.pixelaimbot.*
import com.pixelaimbot.PixelHax.CAPTURE_HEIGHT
import com.pixelaimbot.PixelHax.CAPTURE_OFFSET_X
import com.pixelaimbot.PixelHax.CAPTURE_OFFSET_Y
import com.pixelaimbot.PixelHax.CAPTURE_WIDTH
import com.pixelaimbot.PixelHax.FRAME_CONVERTER
import com.pixelaimbot.PixelHax.FRAME_GRABBER
import com.pixelaimbot.util.Screen
import com.pixelaimbot.util.keyPressed
import com.pixelaimbot.util.moveMouse
import com.pixelaimbot.util.toRGB
import com.pixelaimbot.util.Vector
import com.pixelaimbot.util.Angle
import com.pixelaimbot.util.pathAim
import com.pixelaimbot.util.*
import com.pixelaimbot.settings.*
import java.lang.Math.*
import java.util.concurrent.ThreadLocalRandom.current as tlr
import com.sun.jna.platform.win32.WinDef.POINT

private val pixels = Array(CAPTURE_HEIGHT) { IntArray(CAPTURE_WIDTH) } // zero-garbage reuse optimization
private val speedLocal = speed / 10.0 // locality optimization


// Count the number of consecutive horizontal pixels within the color range, then use the center pixel, which is the first horizontal pixel plus half the total spanning red pixels.
fun checkframe() {
	if (keyPressed(aimKey)) {
		val frame = FRAME_GRABBER.grabImage() ?: return
		val img = FRAME_CONVERTER.convert(frame) ?: return

		val yAxis = img.toRGB(pixels)
		y@ for (y in 0..yAxis.lastIndex) {
			val xAxis = yAxis[y]
			for (x in 0..xAxis.lastIndex) {
				val rgb = xAxis[x] and 0xFF_FF_FF
				if (UseColorRate){
					var r_p = (((rgb and 0xFF0000) shr 16)*100)/255
					var g_p = (((rgb and 0xFF00) shr 8)*100)/255
					var b_p = ((rgb and 0xFF)*100)/255
					if ((r_p >= RATE_RED_MIN && r_p <= RATE_RED_MAX) && g_p <= RATE_GREEN_MAX && b_p <= RATE_BLUE_MAX && (abs(rgb - 0xFF_00_13) >= 445)){
						val absX = x + CAPTURE_OFFSET_X + X_OFFSET
						val absY = y + CAPTURE_OFFSET_Y + Y_OFFSET
						aim(absX, absY)
						break@y
					}
				}
				else{
					if (abs(rgb - COLOR) <= COLOR_TOLERANCE) {
						val absX = x + X_OFFSET + CAPTURE_OFFSET_X
						val absY = y + Y_OFFSET + CAPTURE_OFFSET_Y
						aim(absX, absY)
						break@y
					}
				}
			}
		}
	}
}

fun randLong(min: Long) = tlr().nextLong(min)

private fun aim(absX: Int, absY: Int) {
	val dX = absX - Screen.CENTER_X
	val dY = absY - Screen.CENTER_Y
	
	var dirX = if (dX == 0) 0.0 else speedLocal
	if (dX < 0) dirX = -dirX
	
	var dirY = if (dY == 0) 0.0 else speedLocal
	if (dY < 0) dirY = -dirY
	
	move(dX, dY, dirX, dirY) // split for JIT compile optimization
}

private fun move(dX: Int, dY: Int, dirX: Double, dirY: Double) {
	val moveX = dX * (dirX * dirX)
	val moveY = dY * (dirY * dirY)

	val limitXa = (Screen.WIDTH*786)/1600 // 1600 -> 786 // n -> x // (n*786)/1600 
	val limitXb = (Screen.WIDTH*926)/1600
	val limitYa = (Screen.HEIGHT*574)/900
	val limitYb = (Screen.HEIGHT*600)/900

	if ((moveX >= limitXa && moveX <= limitXb) && (moveY >= limitYa && moveY <= limitYb) && (Overwatch && UseColorRate))
		return

	if ((moveX > 0 || moveY > 0) && moveX <= CAPTURE_WIDTH && moveY <= CAPTURE_HEIGHT)
		if (Aimbot) moveMouse(moveX.toInt(), moveY.toInt())

	if ((moveX <= moveX && moveY <= moveY) && (moveX >= -moveX)) { //&& moveX <= CAPTURE_WIDTH && moveY <= CAPTURE_HEIGHT) {
		if (Triggerbot) {
			mouse(MOUSEEVENTF_LEFTDOWN)
			if (Aimbot == false) Thread.sleep(8 + randLong(10))
			mouse(MOUSEEVENTF_LEFTUP)
		}
	}
}