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

import com.overwatcheat.framegrab.FrameHandler
import org.bytedeco.javacv.Frame
import java.nio.ByteBuffer

class AimFrameHandler(val colorMatcher: AimColorMatcher) : FrameHandler {

    override fun handle(frame: Frame) {
        val frameWidth = frame.imageWidth
        val frameHeight = frame.imageHeight
        val data = frame.image[0] as ByteBuffer

        var startColorCoord = 0L
        var highestX = -1
        var yCount = 0
        for (y in 0..frameHeight - 1) {
            val dataIndexBase = frameWidth * y * 3
            if (startColorCoord != 0L && ++yCount >= 10) break
            for (x in 0..frameWidth - 1) {
                val dataIndex = dataIndexBase + (x * 3)
                val colorCoord = colorCoord(data, dataIndex, x, y)
                if (colorCoord == 0L) continue
                if (startColorCoord == 0L) startColorCoord = colorCoord
                if (x > highestX) highestX = x
            }
        }


        if (startColorCoord == 0L/* || size == 0*/) {
            AimBotState.aimData = 0
        } else {
            val startX = (startColorCoord ushr 32).toInt() and 0xFFFF
            val size = if (highestX != -1) (highestX - startX) and 0xFFFF else 0
            AimBotState.aimData = startColorCoord or size.toLong()
            //println("$size vs $startX vs $highestX")
        }
    }

    private fun colorCoord(data: ByteBuffer, dataIndex: Int, x: Int, y: Int): Long {
        val blue = data[dataIndex].toInt() and 0xFF
        val green = data[dataIndex + 1].toInt() and 0xFF
        val red = data[dataIndex + 2].toInt() and 0xFF

        val rgb = (red shl 16) or (green shl 8) or blue

        return if (colorMatcher.colorMatches(rgb))
            ((x and 0xFFFF).toLong() shl 32) or ((y and 0xFFFF).toLong() shl 16)
        else 0
    }

}