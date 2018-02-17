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

@file:JvmName("Pixelaimbot")

package com.pixelaimbot

import com.pixelaimbot.aimbot.checkframe
import com.pixelaimbot.util.FastRandom
import com.pixelaimbot.util.Screen
import com.pixelaimbot.util.Dojo
import com.pixelaimbot.settings.*
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FrameConverter
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage
import java.io.File
import java.io.FileReader
import java.util.*

const val SETTINGS_DIRECTORY = "settings"

object PixelHax {
	
	lateinit var FRAME_GRABBER: FFmpegFrameGrabber
	lateinit var FRAME_CONVERTER: FrameConverter<BufferedImage>
	const val SETTINGS_DIRECTORY = "settings"
	
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
		System.setProperty("kotlin.compiler.jar", "kotlin-compiler.jar")

		loadSettings()

		Thread.sleep(900)
		
		CAPTURE_WIDTH = (Screen.WIDTH / boxWidthDivisor).toInt()
		CAPTURE_HEIGHT = (Screen.HEIGHT / boxHeightDivisor).toInt()
		
		CAPTURE_OFFSET_X = (Screen.WIDTH / 2) - (CAPTURE_WIDTH / 2)
		CAPTURE_OFFSET_Y = (Screen.HEIGHT / 2) - (CAPTURE_HEIGHT / 2)
		
		FRAME_GRABBER = FFmpegFrameGrabber("title=" + Title).apply {
			format = "gdigrab"
			frameRate = FPS.toDouble()
			imageWidth = CAPTURE_WIDTH
			imageHeight = CAPTURE_HEIGHT
			
			setOption("offset_x", CAPTURE_OFFSET_X.toString())
			setOption("offset_y", CAPTURE_OFFSET_Y.toString())
			
			start()
		}
		
		FRAME_CONVERTER = Java2DFrameConverter()

		
		do {
			checkframe()
			
			val sleepTime = sleepMin + FastRandom[sleepMax - sleepMin]
			if (sleepTime > 0) Thread.sleep(sleepTime.toLong())
		} while (!Thread.interrupted())
	}

	private fun loadSettings() {
		File(SETTINGS_DIRECTORY).listFiles().forEach {
			FileReader(it).use {
				Dojo.script(it
						.readLines()
						.joinToString("\n"))
			}
		}
	}
}