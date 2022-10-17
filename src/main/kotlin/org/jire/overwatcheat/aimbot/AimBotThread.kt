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

package org.jire.overwatcheat.aimbot

import org.jire.overwatcheat.FastRandom
import org.jire.overwatcheat.Keyboard
import org.jire.overwatcheat.Mouse
import org.jire.overwatcheat.settings.Settings
import java.util.concurrent.ThreadLocalRandom
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.system.measureNanoTime

class AimBotThread(
    val captureCenterX: Int, val captureCenterY: Int,
    val maxSnapX: Int, val maxSnapY: Int,
) : Thread("Aim Bot") {

    val aimDurationNanos = (Settings.aimDurationMillis * 1_000_000)

    val random = FastRandom()

    override fun run() {
        val tlr = ThreadLocalRandom.current()
        while (!interrupted()) {
            val elapsed = measureNanoTime {
                if (!Keyboard.keyPressed(Settings.aimKey)) {
                    AimBotState.aimData = 0
                    return@measureNanoTime
                } else if (Settings.aimMode == 1) {
                    AimBotState.flicking = true
                }
                useAimData(AimBotState.aimData)
            }
            val sleepTimeMultiplier = max(
                Settings.aimDurationMultiplierMax,
                (Settings.aimDurationMultiplierBase + tlr.nextFloat())
            )
            val sleepTime = (aimDurationNanos * sleepTimeMultiplier).toLong() - elapsed
            if (sleepTime > 0) {
                val millis = sleepTime / 1_000_000
                val nanos = sleepTime % 1_000_000
                sleep(millis, nanos.toInt())
            }
        }
    }

    private fun useAimData(aimData: Long) {
        if (aimData == 0L) return

        val dX = calculateDelta(
            aimData, 48,
            Settings.aimMinTargetWidth, Settings.aimOffsetX, captureCenterX
        )
        val dY = calculateDelta(
            aimData, 16,
            Settings.aimMinTargetHeight, Settings.aimOffsetY, captureCenterY
        )
        performAim(dX, dY)
    }

    private fun extractAimData(aimData: Long, shiftBits: Int) = (aimData ushr shiftBits) and 0xFFFF
    private fun calculateOffset(size: Long, offset: Float) = size / 2 * offset
    private fun calculateAim(base: Long, offset: Float) = (base + offset).toInt()

    private fun calculateDelta(
        aimData: Long,
        shiftBitsBase: Int,
        minimumSize: Int,
        offset: Float,
        deltaSubtrahend: Int
    ): Int {
        val low = extractAimData(aimData, shiftBitsBase)
        val high = extractAimData(aimData, shiftBitsBase - 16)
        val size = high - low
        if (size < minimumSize) return Int.MAX_VALUE

        val deltaOffset = calculateOffset(size, offset)
        val aimX = calculateAim(low, deltaOffset)

        return aimX - deltaSubtrahend
    }

    private fun performAim(dX: Int, dY: Int) {
        if (abs(dX) > maxSnapX || abs(dY) > maxSnapY) return

        val randomSensitivityMultiplier = 1F - (random[Settings.aimJitterPercent] / 100F)
        val moveX = (dX / Settings.sensitivity * randomSensitivityMultiplier).toInt()
        val moveY = (dY / Settings.sensitivity * randomSensitivityMultiplier).toInt()
        Mouse.move(
            min(Settings.aimMaxMovePixels, moveX),
            min(Settings.aimMaxMovePixels, moveY),
            Settings.deviceId
        )

        if (AimBotState.flicking && abs(moveX) < Settings.flickPixels && abs(moveY) < Settings.flickPixels) {
            AimBotState.flicking = false
            Mouse.click(Settings.deviceId)
            sleep(Settings.flickPause.toLong(), 0)
        }
    }

}