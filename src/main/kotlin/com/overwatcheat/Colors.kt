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

import com.overwatcheat.Overwatcheat.SETTINGS
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import it.unimi.dsi.fastutil.ints.IntSet

object Colors {

    private val set: IntSet = IntOpenHashSet()

    fun colorMatches(rgb: Int) = set.contains(rgb)

    fun init() {
        val tolerance = SETTINGS.targetColorTolerance
        val rgbs = SETTINGS.targetColors
        for (rgb in rgbs) {
            val r = (rgb and 0xFF_00_00) ushr 16
            val g = (rgb and 0x00_FF_00) ushr 8
            val b = rgb and 0x00_00_FF

            for (ri in -tolerance..tolerance) {
                val nr = r + ri
                for (gi in -tolerance..tolerance) {
                    val ng = g + gi
                    for (bi in -tolerance..tolerance) {
                        val nb = b + bi
                        val nrgb = ((nr and 0xFF) shl 16) or ((ng and 0xFF) shl 8) or (nb and 0xFF)
                        set.add(nrgb)
                    }
                }
            }
        }
    }

}