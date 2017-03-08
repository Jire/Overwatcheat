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

package com.overwatcheat.util

import com.overwatcheat.util.User32.mouse_event

const val MOUSEEVENTF_MOVE = 0x0001
const val MOUSEEVENTF_ABSOLUTE = 0x8000

fun moveMouse(x: Int, y: Int, flags: Int = MOUSEEVENTF_MOVE) = mouse_event(flags, x, y, 0, 0)