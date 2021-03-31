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
import kotlin.math.abs
import kotlin.system.measureTimeMillis

class AimBotThread(
    val aimKey: Int,
    val sensitivity: Float,
    val aimDurationMillis: Long,
    val aimJitterPercent: Int,
    val captureCenterX: Int, val captureCenterY: Int,
    val aimOffsetX: Float, val aimOffsetY: Float,
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
                val colorCoord = AimBotState.aimData
                if (colorCoord == 0L) return@measureTimeMillis

                val xLow = (colorCoord ushr 48) and 0xFFFF
                val xHigh = (colorCoord ushr 32) and 0xFFFF
                val yLow = (colorCoord ushr 16) and 0xFFFF
                val yHigh = colorCoord and 0xFFFF

                val aimX = (xLow + ((xHigh - xLow) / 2 * aimOffsetX)).toInt()
                val aimY = (yLow + ((yHigh - yLow) / 2 * aimOffsetY)).toInt()

                val dX = aimX - captureCenterX
                val dY = aimY - captureCenterY

                if (abs(dX) > maxSnapX || abs(dY) > maxSnapY) return@measureTimeMillis

                //val randomSensitivityMultiplier = 1F - (random[aimJitterPercent] / 100F)
                Mouse.move(
                    (dX / sensitivity/* * randomSensitivityMultiplier*/).toInt(),
                    (dY / sensitivity/* * randomSensitivityMultiplier*/).toInt(),
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