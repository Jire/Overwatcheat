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

import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import it.unimi.dsi.fastutil.ints.IntSet

class AimColorMatcher(
    val targetColorTolerance: Int,
    vararg val targetColors: Int
) {

    val matchSet: IntSet = IntOpenHashSet()

    fun initializeMatchSet() {
        val toleranceRange = -targetColorTolerance..targetColorTolerance
        for (rgb in targetColors) {
            val baseR = (rgb and 0xFF_00_00) ushr 16
            val baseG = (rgb and 0x00_FF_00) ushr 8
            val baseB = rgb and 0x00_00_FF

            for (ri in toleranceRange) {
                val matchR = baseR + ri
                for (gi in toleranceRange) {
                    val matchG = baseG + gi
                    for (bi in toleranceRange) {
                        val matchB = baseB + bi
                        val matchRGB = ((matchR and 0xFF) shl 16) or
                                ((matchG and 0xFF) shl 8) or
                                (matchB and 0xFF)
                        matchSet.add(matchRGB)
                    }
                }
            }
        }
    }

    fun colorMatches(rgb: Int) = matchSet.contains(rgb)

}