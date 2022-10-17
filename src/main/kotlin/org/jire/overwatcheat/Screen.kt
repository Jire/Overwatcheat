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

import java.awt.Dimension
import java.awt.Toolkit

object Screen {

    private val DIMENSION: Dimension = Toolkit.getDefaultToolkit().screenSize

    val WIDTH = DIMENSION.width
    val HEIGHT = DIMENSION.height

    const val OVERLAY_OFFSET = 1
    val OVERLAY_WIDTH = WIDTH - OVERLAY_OFFSET
    val OVERLAY_HEIGHT = HEIGHT - OVERLAY_OFFSET

}