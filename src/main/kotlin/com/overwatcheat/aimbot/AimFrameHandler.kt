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

import com.overwatcheat.Keyboard
import com.overwatcheat.Mouse
import com.overwatcheat.framegrab.FrameHandler
import org.bytedeco.javacv.Frame
import java.nio.ByteBuffer
import kotlin.math.abs

class AimFrameHandler(
    val aimKey: Int,
    val colorMatcher: AimColorMatcher,
    val sensitivity: Float,
    val captureCenterX: Int, val captureCenterY: Int,
    val aimOffsetX: Int, val aimOffsetY: Int,
    val maxSnapX: Float, val maxSnapY: Float,
    val deviceID: Int
) : FrameHandler {

    override fun handle(frame: Frame) {
        if (!Keyboard.keyPressed(aimKey)) return

        val frameWidth = frame.imageWidth
        val frameHeight = frame.imageHeight
        val data = frame.image[0] as ByteBuffer

        y@ for (y in 0..frameHeight - 1) {
            val dataIndexBase = frameWidth * y * 3
            for (x in 0..frameWidth - 1) {
                val dataIndex = dataIndexBase + (x * 3)
                if (usedDataIndex(data, dataIndex, x, y)) return
            }
        }
    }

    private fun usedDataIndex(data: ByteBuffer, dataIndex: Int, x: Int, y: Int): Boolean {
        val blue = data[dataIndex].toInt() and 0xFF
        val green = data[dataIndex + 1].toInt() and 0xFF
        val red = data[dataIndex + 2].toInt() and 0xFF

        val rgb = (red shl 16) or (green shl 8) or blue
        if (!colorMatcher.colorMatches(rgb)) return false

        val deltaX = x - captureCenterX + aimOffsetX
        if (abs(deltaX) >= maxSnapX) return false
        val deltaY = y - captureCenterY + aimOffsetY
        if (abs(deltaY) >= maxSnapY) return false

        Mouse.move(
            (deltaX / sensitivity).toInt(),
            (deltaY / sensitivity).toInt(),
            deviceID
        )
        return true
    }

}