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

package com.overwatcheat

import java.io.File

class Settings(
    val aimKey: Int, val speed: Double,
    val boxWidthDivisor: Double, val boxHeightDivisor: Double,
    val targetColor: Int, val targetColorTolerance: Int,
    val windowTitleSearch: String,
    val deviceId: Int
) {

    val targetColorRed = (targetColor and 0xFF_00_00) ushr 16
    val targetColorGreen = (targetColor and 0x00_FF_00) ushr 8
    val targetColorBlue = targetColor and 0x00_00_FF

    companion object {

        const val DEFAULT_FILE = "overwatcheat.cfg"

        fun read(filePath: String = DEFAULT_FILE) = read(File(filePath))

        fun read(file: File): Settings {
            var aimKey = 1

            var speed = 4.0

            var boxWidthDivisor = 6.0
            var boxHeightDivisor = 3.5

            var targetColor = Integer.parseInt("e23be9", 16)
            var targetColorTolerance = 12

            var windowTitleSearch = "Overwatch"

            var deviceId = 11

            file.readLines().forEach {
                if (it.contains("=")) {
                    val split = it.split("=")
                    when (split[0]) {
                        "aim_key" -> aimKey = split[1].toInt()
                        "speed" -> speed = split[1].toDouble()
                        "box_width_divisor" -> boxWidthDivisor = split[1].toDouble()
                        "box_height_divisor" -> boxHeightDivisor = split[1].toDouble()
                        "target_color" -> targetColor = Integer.parseInt(split[1], 16)
                        "target_color_tolerance" -> targetColorTolerance = split[1].toInt()
                        "window_title_search" -> windowTitleSearch = split[1]
                        "device_id" -> deviceId = split[1].toInt()
                    }
                }
            }

            return Settings(
                aimKey,
                speed,
                boxWidthDivisor,
                boxHeightDivisor,
                targetColor,
                targetColorTolerance,
                windowTitleSearch,
                deviceId
            )
        }
    }

}