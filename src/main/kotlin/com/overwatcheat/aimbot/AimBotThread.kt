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

package com.overwatcheat.aimbot

import com.overwatcheat.FastRandom
import com.overwatcheat.Keyboard
import com.overwatcheat.Mouse
import kotlin.math.min
import kotlin.system.measureTimeMillis

class AimBotThread(
    val aimKey: Int,
    val sensitivity: Float,
    val aimDurationMillis: Long,
    val aimJitterPercent: Int,
    val captureCenterX: Int, val captureCenterY: Int,
    val aimOffsetX: Int, val aimOffsetY: Int,
    val maxSnapX: Int, val maxSnapY: Int,
    val deviceID: Int
) : Thread("Aim Bot") {

    val random = FastRandom()

    override fun run() {
        while (!interrupted()) {
            val elapsed = measureTimeMillis {
                if (!Keyboard.keyPressed(aimKey)) {
                    AimBotState.aimData = 0
                    return@measureTimeMillis
                }
//65, 45, 43
                val colorCoord = AimBotState.aimData
                if (colorCoord == 0L) return@measureTimeMillis
//size=127, 56,66
                val size = colorCoord and 0xFFFF
                if (size < 20 || size > 220) return@measureTimeMillis
                val scale = if (size < 20 || size > 220) 1F else size / 65.0F
                //size=109, 56, 36
//size=110, 53,53
                val colorX = (colorCoord ushr 32).toInt() and 0xFFFF
                val deltaX = min(maxSnapX, colorX - captureCenterX + (aimOffsetX * scale).toInt())

                val colorY = (colorCoord ushr 16).toInt() and 0xFFFF
                val deltaY = min(maxSnapY, colorY - captureCenterY + (aimOffsetY * scale).toInt())

                //println("($colorX,$colorY) size=$size scale=$scale")

                val randomSensitivityMultiplier = 1F//1F - (random[aimJitterPercent] / 100F)
                Mouse.move(
                    (deltaX / sensitivity * randomSensitivityMultiplier).toInt(),
                    (deltaY / sensitivity * randomSensitivityMultiplier).toInt(),
                    deviceID
                )
            }
            val sleepTime = aimDurationMillis/* + random[2]*/ - elapsed
            if (sleepTime > 0) {
                sleep(sleepTime)
            }
        }
    }

}