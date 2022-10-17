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

package org.jire.overwatcheat

import org.jire.overwatcheat.aimbot.AimBotThread
import org.jire.overwatcheat.aimbot.AimColorMatcher
import org.jire.overwatcheat.aimbot.AimFrameHandler
import org.jire.overwatcheat.framegrab.FrameGrabber
import org.jire.overwatcheat.framegrab.FrameGrabberThread
import org.jire.overwatcheat.framegrab.FrameHandler
import org.jire.overwatcheat.overlay.OverlayManager
import org.jire.overwatcheat.settings.Settings

object Main {

    @JvmStatic
    fun main(args: Array<String>) {
        Settings.read()

        val captureWidth = Settings.boxWidth
        val captureHeight = Settings.boxHeight

        val captureOffsetX = (Screen.WIDTH - captureWidth) / 2
        val captureOffsetY = (Screen.HEIGHT - captureHeight) / 2

        val captureCenterX = captureWidth / 2
        val captureCenterY = captureHeight / 2

        val aimColorMatcher = AimColorMatcher()
        aimColorMatcher.initializeMatchSet()

        val frameHandler: FrameHandler = AimFrameHandler(aimColorMatcher)

        val frameGrabber = FrameGrabber(
            Settings.windowTitleSearch,
            Settings.fps,
            captureWidth,
            captureHeight,
            captureOffsetX,
            captureOffsetY
        )

        val frameGrabberThread = FrameGrabberThread(frameGrabber, frameHandler)

        val maxSnapX = (captureWidth / Settings.maxSnapDivisor).toInt()
        val maxSnapY = (captureHeight / Settings.maxSnapDivisor).toInt()

        val aimBotThread = AimBotThread(
            captureCenterX, captureCenterY,
            maxSnapX, maxSnapY
        )

        frameGrabberThread.start()
        aimBotThread.start()

        if (Settings.enableOverlay) {
            OverlayManager.open(captureOffsetX, captureOffsetY)
        }
    }

}