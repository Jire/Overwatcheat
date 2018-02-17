package com.pixelaimbot.util

data class Vector(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {
	
	fun set(x: Double, y: Double, z: Double) = apply {
		this.x = x
		this.y = y
		this.z = z
	}
	
}