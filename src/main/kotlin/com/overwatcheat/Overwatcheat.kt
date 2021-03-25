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

@file:JvmName("Overwatcheat")

package com.overwatcheat

import com.overwatcheat.nativelib.HWNDFinder
import com.overwatcheat.nativelib.interception.Mouse
import com.overwatcheat.util.Keyboard.keyPressed
import com.overwatcheat.util.Screen
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FrameConverter
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import kotlin.math.abs

object Overwatcheat {

    lateinit var SETTINGS: Settings
    lateinit var FRAME_GRABBER: FFmpegFrameGrabber
    lateinit var FRAME_CONVERTER: FrameConverter<BufferedImage>

    var CAPTURE_WIDTH = 0
        private set
    var CAPTURE_HEIGHT = 0
        private set

    var CAPTURE_OFFSET_X = 0
        private set
    var CAPTURE_OFFSET_Y = 0
        private set

    var CAPTURE_CENTER_X = 0
        private set
    var CAPTURE_CENTER_Y = 0
        private set

    @JvmStatic
    fun main(args: Array<String>) {
        Thread.currentThread().priority = Thread.MAX_PRIORITY

        SETTINGS = Settings.read() // load settings

        CAPTURE_WIDTH = (Screen.WIDTH / SETTINGS.boxWidthDivisor).toInt()
        CAPTURE_HEIGHT = (Screen.HEIGHT / SETTINGS.boxHeightDivisor).toInt()

        val MAX_SNAP_X = CAPTURE_WIDTH / SETTINGS.maxSnapDivisor
        val MAX_SNAP_Y = CAPTURE_HEIGHT / SETTINGS.maxSnapDivisor

        CAPTURE_OFFSET_X = (Screen.WIDTH - CAPTURE_WIDTH) / 2
        CAPTURE_OFFSET_Y = (Screen.HEIGHT - CAPTURE_HEIGHT) / 2

        CAPTURE_CENTER_X = CAPTURE_WIDTH / 2
        CAPTURE_CENTER_Y = CAPTURE_HEIGHT / 2

        FRAME_GRABBER = FFmpegFrameGrabber("title=${HWNDFinder.projectorWindowTitle}").apply {
            format = "gdigrab"
            frameRate = SETTINGS.fps
            imageWidth = CAPTURE_WIDTH
            imageHeight = CAPTURE_HEIGHT

            setOption("offset_x", CAPTURE_OFFSET_X.toString())
            setOption("offset_y", CAPTURE_OFFSET_Y.toString())

            start()
        }

        FRAME_CONVERTER = Java2DFrameConverter()
        Colors.init()

        frames@ while (true) {
            val frame = FRAME_GRABBER.grabImage()

            if (!keyPressed(SETTINGS.aimKey)) continue

            val img = FRAME_CONVERTER.convert(frame)
            val imgWidth = img.width
            val imgHeight = img.height
            val pixels = (img.raster.dataBuffer as DataBufferByte).data

            y@ for (y in 0..imgHeight - 1) {
                val pixelY = imgWidth * y * 3
                for (x in 0..imgWidth - 1) {
                    val pixel = pixelY + (x * 3)
                    val rgb = (pixels[pixel].toInt() and 0xFF) or // blue
                            ((pixels[pixel + 1].toInt() and 0xFF) shl 8) or // green
                            ((pixels[pixel + 2].toInt() and 0xFF) shl 16) // red
                    if (!Colors.colorMatches(rgb)) continue

                    val dX = x - CAPTURE_CENTER_X + X_OFFSET
                    if (abs(dX) >= MAX_SNAP_X) continue
                    val dY = y - CAPTURE_CENTER_Y + Y_OFFSET
                    if (abs(dY) >= MAX_SNAP_Y) continue

                    Mouse.move((dX / SETTINGS.sensitivity).toInt(), (dY / SETTINGS.sensitivity).toInt())
                    continue@frames
                }
            }
        }
    }

}