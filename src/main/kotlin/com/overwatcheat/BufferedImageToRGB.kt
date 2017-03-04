package com.overwatcheat

import java.awt.image.DataBufferByte
import java.awt.image.BufferedImage

fun BufferedImage.toRGB(): Array<IntArray> {
	val pixels = (raster.dataBuffer as DataBufferByte).data
	val hasAlphaChannel = alphaRaster != null
	
	val result = Array(height) { IntArray(width) }
	if (hasAlphaChannel) {
		val pixelLength = 4
		var pixel = 0
		var row = 0
		var col = 0
		while (pixel < pixels.size) {
			var argb = 0
			argb += pixels[pixel].toInt() and 0xff shl 24 // alpha
			argb += pixels[pixel + 1].toInt() and 0xff // blue
			argb += pixels[pixel + 2].toInt() and 0xff shl 8 // green
			argb += pixels[pixel + 3].toInt() and 0xff shl 16 // red
			result[row][col] = argb
			col++
			if (col == width) {
				col = 0
				row++
			}
			pixel += pixelLength
		}
	} else {
		val pixelLength = 3
		var pixel = 0
		var row = 0
		var col = 0
		while (pixel < pixels.size) {
			var argb = 0
			argb += -16777216 // 255 alpha
			argb += pixels[pixel].toInt() and 0xff // blue
			argb += pixels[pixel + 1].toInt() and 0xff shl 8 // green
			argb += pixels[pixel + 2].toInt() and 0xff shl 16 // red
			result[row][col] = argb
			col++
			if (col == width) {
				col = 0
				row++
			}
			pixel += pixelLength
		}
	}
	
	return result
}