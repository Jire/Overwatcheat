package com.overwatcheat

import java.io.File

class Settings(val aimKey: Int, val speed: Double,
               val sleepMin: Int, val sleepMax: Int,
               val boxWidthDivisor: Double, val boxHeightDivisor: Double) {
	
	companion object {
		
		const val DEFAULT_FILE = "overwatcheat.cfg"
		
		fun read(filePath: String = DEFAULT_FILE)  = read(File(filePath))
		
		fun read(file: File): Settings {
			var aimKey = 1
			
			var speed = 4.0
			
			var sleepMin = 2
			var sleepMax = 20
			
			var boxWidthDivisor = 6.0
			var boxHeightDivisor = 3.5
			
			file.readLines().forEach {
				if (it.contains("=")) {
					val split = it.split("=")
					when (split[0]) {
						"aim_key" -> aimKey = split[1].toInt()
						"speed" -> speed = split[1].toDouble()
						"sleep_min" -> sleepMin = split[1].toInt()
						"sleep_max" -> sleepMax = split[1].toInt()
						"box_width_divisor" -> boxWidthDivisor = split[1].toDouble()
						"box_height_divisor" -> boxHeightDivisor = split[1].toDouble()
					}
				}
			}
			
			return Settings(aimKey, speed, sleepMin, sleepMax, boxWidthDivisor, boxHeightDivisor)
		}
	}
	
}