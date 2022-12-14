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

package org.jire.overwatcheat.framegrab

import org.bytedeco.javacv.FFmpegFrameGrabber

class FrameGrabber(
    windowTitleSearch: String,
    frameRate: Double,
    imageWidth: Int,
    imageHeight: Int,
    captureOffsetX: Int, captureOffsetY: Int,
    format: String = "gdigrab",
    filename: String = convertWindowName(windowTitleSearch)
) : FFmpegFrameGrabber(filename) {

    init {
        this.frameRate = frameRate
        this.imageWidth = imageWidth
        this.imageHeight = imageHeight
        this.format = format

        setOption("offset_x", captureOffsetX.toString())
        setOption("offset_y", captureOffsetY.toString())
    }

    private companion object {

        init {
            /*
             * Because javacv comment tryLoad() in the latest version,
             * So we need to load it manually
             *
             * @see FFmpegFrameGrabber static block
             */
            tryLoad()
        }

        private const val DESKTOP = "desktop"

        private fun convertWindowName(windowTitle: String): String =
            if (DESKTOP == windowTitle)
                DESKTOP
            else
                "title=${FrameWindowFinder.findWindowTitle(windowTitle)}"

    }
}
