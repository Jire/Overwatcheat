@file:JvmName("Overwatcheat")

package com.overwatcheat

import com.overwatcheat.aimbot.aimBot
import com.overwatcheat.util.FastRandom
import com.overwatcheat.util.Screen
import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.FrameConverter
import org.bytedeco.javacv.Java2DFrameConverter
import java.awt.image.BufferedImage

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
		
		FRAME_GRABBER = FFmpegFrameGrabber("title=Overwatch").apply {
			format = "gdigrab"
			frameRate = 30.0
			imageWidth = CAPTURE_WIDTH
			imageHeight = CAPTURE_HEIGHT
			
			setOption("offset_x", CAPTURE_OFFSET_X.toString())
			setOption("offset_y", CAPTURE_OFFSET_Y.toString())
			
			start()
		}
		
		FRAME_CONVERTER = Java2DFrameConverter()
		
		do {
			aimBot()
			
			// give the CPU a break
			Thread.sleep((SETTINGS.sleepMin + FastRandom[SETTINGS.sleepMax - SETTINGS.sleepMin]).toLong())
		} while (!Thread.interrupted())
	}
	
}