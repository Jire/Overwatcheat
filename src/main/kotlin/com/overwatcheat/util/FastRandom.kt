package com.overwatcheat.util

import java.util.*

object FastRandom {
	
	private var x = Random().nextLong()
	
	operator fun get(max: Int): Int {
		x = x xor (x shr 12)
		x = x xor (x shl 25)
		x = x xor (x shr 27)
		x *= 2685821657736338717L
		
		val factor = Math.abs(x) / Long.MAX_VALUE.toDouble()
		return (max * factor).toInt()
	}
	
}