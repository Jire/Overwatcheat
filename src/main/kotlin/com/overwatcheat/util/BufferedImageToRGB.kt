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