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

@file:JvmName("Overwatcheat")

package com.overwatcheat

import com.overwatcheat.aimbot.AimBotThread
import com.overwatcheat.aimbot.AimColorMatcher
import com.overwatcheat.aimbot.AimFrameHandler
import com.overwatcheat.framegrab.FrameGrabber
import com.overwatcheat.framegrab.FrameGrabberThread
import com.overwatcheat.framegrab.FrameHandler
import kotlin.math.ceil

object Overwatcheat {

    @JvmStatic
    fun main(args: Array<String>) {
        val settings = Settings.read()

        val captureWidth = 256//(Screen.WIDTH / settings.boxWidthDivisor).toInt()
        val captureHeight = 256//(Screen.HEIGHT / settings.boxHeightDivisor).toInt()

        val captureOffsetX = (Screen.WIDTH - captureWidth) / 2
        val captureOffsetY = (Screen.HEIGHT - captureHeight) / 2

        val captureCenterX = captureWidth / 2
        val captureCenterY = captureHeight / 2

        val aimColorMatcher = AimColorMatcher(
            settings.targetColorTolerance,
            *settings.targetColors
        )
        aimColorMatcher.initializeMatchSet()

        val frameHandler: FrameHandler = AimFrameHandler(aimColorMatcher)

        val frameGrabber = FrameGrabber(
            settings.windowTitleSearch,
            settings.fps,
            captureWidth,
            captureHeight,
            captureOffsetX,
            captureOffsetY
        )

        val frameGrabberThread = FrameGrabberThread(frameGrabber, frameHandler)

        val aimOffsetX = ceil(settings.aimOffsetX * (Screen.WIDTH / 2560.0)).toInt()
        val aimOffsetY = ceil(settings.aimOffsetY * (Screen.HEIGHT / 1440.0)).toInt()

        val maxSnapX = (captureWidth / settings.maxSnapDivisor).toInt()
        val maxSnapY = (captureHeight / settings.maxSnapDivisor).toInt()

        val aimBotThread = AimBotThread(
            settings.aimKey,
            settings.sensitivity,
            settings.aimDurationMillis,
            settings.aimJitterPercent,
            captureCenterX, captureCenterY,
            aimOffsetX, aimOffsetY,
            maxSnapX, maxSnapY,
            settings.deviceId
        )

        frameGrabberThread.start()
        aimBotThread.start()
    }

}