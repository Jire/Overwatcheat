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

import com.overwatcheat.util.Screen
import kotlin.math.ceil

const val X_OFFSET_1080p = 18
const val Y_OFFSET_1080p = 40

val X_OFFSET = ceil(X_OFFSET_1080p * (Screen.WIDTH / 1920.0)).toInt()
val Y_OFFSET = ceil(Y_OFFSET_1080p * (Screen.HEIGHT / 1080.0)).toInt()