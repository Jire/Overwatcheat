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

package org.jire.overwatcheat.aimbot

import org.bytedeco.javacv.Frame
import org.jire.overwatcheat.framegrab.FrameHandler
import java.nio.ByteBuffer

class AimFrameHandler(val colorMatcher: AimColorMatcher) : FrameHandler {

    override fun handle(frame: Frame) {
        val frameWidth = frame.imageWidth
        val frameHeight = frame.imageHeight
        val data = frame.image[0] as ByteBuffer

        var found = false
        var xHigh = Int.MIN_VALUE
        var xLow = Int.MAX_VALUE
        var yHigh = Int.MIN_VALUE
        var yLow = Int.MAX_VALUE
        for (x in 0..frameWidth - 1) {
            for (y in 0..frameHeight - 1) {
                val dataIndexBase = frameWidth * y * 3
                val dataIndex = dataIndexBase + (x * 3)
                if (!colorMatches(data, dataIndex)) continue

                found = true
                if (x > xHigh) xHigh = x
                if (x < xLow) xLow = x
                if (y > yHigh) yHigh = y
                if (y < yLow) yLow = y
            }
        }

        AimBotState.aimData =
            if (found)
                (xLow.toLong() shl 48) or
                        (xHigh.toLong() shl 32) or
                        (yLow.toLong() shl 16) or
                        yHigh.toLong()
            else 0
    }

    private fun pixelRGB(data: ByteBuffer, dataIndex: Int): Int {
        val blue = data[dataIndex].toInt() and 0xFF
        val green = data[dataIndex + 1].toInt() and 0xFF
        val red = data[dataIndex + 2].toInt() and 0xFF
        return (red shl 16) or (green shl 8) or blue
    }

    private fun colorMatches(data: ByteBuffer, dataIndex: Int) = colorMatcher.colorMatches(pixelRGB(data, dataIndex))

}