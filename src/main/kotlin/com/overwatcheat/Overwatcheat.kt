@file:JvmName("Overwatcheat")

package com.overwatcheat

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Java2DFrameConverter
import java.lang.Math.abs

const val SPEED = 5.12
const val AIM_KEY = 1 // left click

const val HP_BAR_COLOR = 0xFF_00_13
const val HP_BAR_COLOR_TOLERANCE = 2

const val X_OFFSET_1080p = 55
const val Y_OFFSET_1080p = 54

val X_OFFSET = Math.ceil(X_OFFSET_1080p * (Screen.WIDTH / 1920.0)).toInt()
val Y_OFFSET = Math.ceil(Y_OFFSET_1080p * (Screen.HEIGHT / 1080.0)).toInt()

fun main(args: Array<String>) {
	val frameConverter = Java2DFrameConverter()
	val frameGrabber = FFmpegFrameGrabber("title=Overwatch").apply {
		format = "gdigrab"
		frameRate = 30.0
		imageWidth = (Screen.WIDTH / 1.5).toInt()
		imageHeight = (Screen.HEIGHT / 2.2).toInt()
		
		setVideoOption("offset_x", (Screen.WIDTH / 3.1).toInt().toString())
		setVideoOption("offset_y", (Screen.HEIGHT / 4.23).toInt().toString())
		
		start()
	}
	
	while (!Thread.interrupted()) {
		Thread.sleep(1L) // give the CPU a break
		
		if (keyReleased(AIM_KEY)) {
			Thread.sleep(50)
			continue
		}
		
		val frame = frameGrabber.grabImage() ?: continue
		val img = frameConverter.convert(frame) ?: continue
		
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
	if ((moveX != 0.0 || moveY != 0.0) && moveX < 300 && moveY < 300)
		mouse(moveX.toInt(), moveY.toInt())
}