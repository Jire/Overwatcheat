@file:JvmName("Overwatcheat")

package com.overwatcheat

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import java.lang.Math.abs

const val AIM_KEY = 1

const val HP_BAR_COLOR = 0xFF_00_13
const val HP_BAR_COLOR_TOLERANCE = 2

const val SPEED = 6.0

const val X_OFFSET = 55
const val Y_OFFSET = 54

fun main(args: Array<String>) {
	val frameConverter = Java2DFrameConverter()
	val frameGrabber = FFmpegFrameGrabber("title=Overwatch").apply {
		format = "gdigrab"
		frameRate = 30.0
		imageWidth = Screen.WIDTH
		imageHeight = Screen.HEIGHT
		
		start()
	}
	
	while (!Thread.interrupted()) {
		if (keyReleased(AIM_KEY)) {
			Thread.sleep(50)
			continue
		}
		
		val frame = frameGrabber.grabImage()
		val img = frameConverter.convert(frame)
		
		val yAxis = img.toRGB()
		
		y@ for (y in 0..yAxis.lastIndex) {
			val xAxis = yAxis[y]
			x@ for (x in 0..xAxis.lastIndex) {
				val rgb = xAxis[x] and 0xFF_FF_FF
				if (abs(rgb - HP_BAR_COLOR) <= HP_BAR_COLOR_TOLERANCE) {
					aim(x + X_OFFSET, y + Y_OFFSET)
					break@y
				}
			}
		}
		
		Thread.sleep(1L) // give the CPU a break
	}
}

private fun aim(absX: Int, absY: Int) {
	val dX = absX - Screen.CENTER_X
	val dY = absY - Screen.CENTER_Y
	
	val xEnd = dX + 4
	val yEnd = dY + 2
	
	var dirX = if (xEnd == 0) 0.0 else SPEED / 10.0
	if (xEnd < 0) dirX = -dirX
	
	var dirY = if (yEnd == 0) 0.0 else SPEED / 10.0
	if (yEnd < 0) dirY = -dirY
	
	val moveX = dX * (dirX * dirX)
	val moveY = dY * (dirY * dirY)
	if (moveX != 0.0 || moveY != 0.0)
		mouse(moveX.toInt(), moveY.toInt())
}