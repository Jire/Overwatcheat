package com.overwatcheat.util

import java.awt.image.DataBufferByte
import java.awt.image.BufferedImage

fun BufferedImage.toRGB(output: Array<IntArray>): Array<IntArray> {
	val pixels = (raster.dataBuffer as DataBufferByte).data
	
	var pixel = 0
	var row = 0
	var col = 0
	
	while (pixel < pixels.size) {
		var argb = 0
		argb += -0x1_00_00_00 // 255 alpha
		argb += pixels[pixel].toInt() and 0xFF // blue
		argb += pixels[pixel + 1].toInt() and 0xFF shl 8 // green
		argb += pixels[pixel + 2].toInt() and 0xFF shl 16 // red
		output[row][col] = argb
		
		col++
		if (col == width) {
			col = 0
			row++
		}
		
		pixel += 3 // pixel length
	}
	
	return output
}