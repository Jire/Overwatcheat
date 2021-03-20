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

package com.overwatcheat.aimbot

import com.overwatcheat.*
import com.overwatcheat.Overwatcheat.CAPTURE_HEIGHT
import com.overwatcheat.Overwatcheat.CAPTURE_OFFSET_X
import com.overwatcheat.Overwatcheat.CAPTURE_OFFSET_Y
import com.overwatcheat.Overwatcheat.CAPTURE_WIDTH
import com.overwatcheat.Overwatcheat.SETTINGS
import com.overwatcheat.nativelib.interception.Mouse
import com.overwatcheat.util.Screen
import com.overwatcheat.util.keyPressed
import kotlin.math.abs

val pixels = Array(CAPTURE_HEIGHT) { IntArray(CAPTURE_WIDTH) } // zero-garbage reuse optimization

fun aimBot() {
    if (!keyPressed(SETTINGS.aimKey)) return
    val yAxis = Overwatcheat.CaptureThread.yAxis ?: return
    y@ for (y in 0..yAxis.lastIndex) {
        val xAxis = yAxis[y]
        for (x in 0..xAxis.lastIndex) {
            val rgb = xAxis[x] and 0xFF_FF_FF

            val r = (rgb and 0xFF_00_00) ushr 16
            val g = (rgb and 0x00_FF_00) ushr 8
            val b = rgb and 0x00_00_FF

            val sum = abs(r - SETTINGS.targetColorRed) +
                    abs(g - SETTINGS.targetColorGreen) +
                    abs(b - SETTINGS.targetColorBlue)
            if (sum <= SETTINGS.targetColorTolerance) {
                val absX = x + X_OFFSET + CAPTURE_OFFSET_X
                val absY = y + Y_OFFSET + CAPTURE_OFFSET_Y
                aim(absX, absY)
                break@y
            }
        }
    }
    Overwatcheat.CaptureThread.yAxis = null
}

private fun aim(absX: Int, absY: Int) {
    val dX = absX - Screen.CENTER_X
    val dY = absY - Screen.CENTER_Y

    var dirX = if (dX == 0) 0.0 else SETTINGS.speed
    if (dX < 0) dirX = -dirX

    var dirY = if (dY == 0) 0.0 else SETTINGS.speed
    if (dY < 0) dirY = -dirY

    move(dX, dY, dirX, dirY) // split for JIT compile optimization
}

private fun move(dX: Int, dY: Int, dirX: Double, dirY: Double) {
    val moveX = dX * (dirX * dirX)
    val moveY = dY * (dirY * dirY)
    if ((moveX > 0 || moveY > 0)
        && moveX <= Overwatcheat.MOVE_X_MAX
        && moveY <= Overwatcheat.MOVE_Y_MAX
    ) {
        Mouse.move(moveX.toInt(), moveY.toInt())
    }
}