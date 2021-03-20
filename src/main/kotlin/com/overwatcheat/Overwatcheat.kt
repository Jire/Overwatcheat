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

import com.overwatcheat.aimbot.aimBot
import com.overwatcheat.aimbot.pixels
import com.overwatcheat.nativelib.HWNDFinder
import com.overwatcheat.util.Screen
import com.overwatcheat.util.toRGB
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FrameConverter
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage
import kotlin.system.measureTimeMillis

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

    @JvmStatic
    fun main(args: Array<String>) {
        SETTINGS = Settings.read() // load settings

        CAPTURE_WIDTH = (Screen.WIDTH / SETTINGS.boxWidthDivisor).toInt()
        CAPTURE_HEIGHT = (Screen.HEIGHT / SETTINGS.boxHeightDivisor).toInt()

        CAPTURE_OFFSET_X = (Screen.WIDTH / 2) - (CAPTURE_WIDTH / 2)
        CAPTURE_OFFSET_Y = (Screen.HEIGHT / 2) - (CAPTURE_HEIGHT / 2)

        FRAME_GRABBER = FFmpegFrameGrabber("title=${HWNDFinder.projectorWindowTitle}").apply {
            format = "gdigrab"
            frameRate = 60.0
            imageWidth = CAPTURE_WIDTH
            imageHeight = CAPTURE_HEIGHT

            setOption("offset_x", CAPTURE_OFFSET_X.toString())
            setOption("offset_y", CAPTURE_OFFSET_Y.toString())

            start()
        }

        FRAME_CONVERTER = Java2DFrameConverter()

        CaptureThread.start()
        do {
            val millis = measureTimeMillis { aimBot() }
            if (millis == 0L) Thread.sleep(1)

            // give the CPU a break
            /*val sleepTime = SETTINGS.sleepMin + FastRandom[SETTINGS.sleepMax - SETTINGS.sleepMin]
            if (sleepTime > 0) Thread.sleep(sleepTime.toLong())*/
        } while (true)
    }

    object CaptureThread : Thread() {

        @Volatile
        var yAxis: Array<IntArray>? = null

        override fun run() {
            do {
                val frame = FRAME_GRABBER.grabFrame(false, true, true, false, false)
                val img = FRAME_CONVERTER.convert(frame)
                yAxis = img.toRGB(pixels)
            } while (true)
        }
    }

}