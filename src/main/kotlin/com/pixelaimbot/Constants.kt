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

package com.pixelaimbot

import com.pixelaimbot.util.Screen
import com.pixelaimbot.settings.*

val HP_BAR_COLOR = COLOR //0xFF_00_13
//const val HP_BAR_COLOR = 0xF6_30_35
//const val HP_BAR_COLOR = 0xFE_00_00
val HP_BAR_COLOR_TOLERANCE = COLOR_TOLERANCE //2

val X_OFFSET_1080p = if (Overwatch) 55 else x_offset_1080p
val Y_OFFSET_1080p = if (Overwatch) 54 else y_offset_1080

val X_OFFSET = Math.ceil(X_OFFSET_1080p * (Screen.WIDTH / 1920.0)).toInt()
val Y_OFFSET = Math.ceil(Y_OFFSET_1080p * (Screen.HEIGHT / 1080.0)).toInt()