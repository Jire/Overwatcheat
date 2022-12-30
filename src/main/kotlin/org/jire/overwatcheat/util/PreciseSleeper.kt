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

package org.jire.overwatcheat.util

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap

enum class PreciseSleeper(val type: Int) {

    YIELD(0) {
        override fun waitSleep() {
            Thread.yield()
        }
    },
    SPIN_WAIT(1) {
        override fun waitSleep() {
            Thread.onSpinWait()
        }
    },
    SLEEP(2) {
        override fun waitSleep() {
            Thread.sleep(0)
        }
    };

    abstract fun waitSleep()

    fun preciseSleep(totalNanos: Long) {
        val startTime = System.nanoTime()
        while (System.nanoTime() - startTime < totalNanos) {
            waitSleep()
        }
    }

    companion object {
        @JvmStatic
        val values = values()

        private val typeToSleeper: Int2ObjectMap<PreciseSleeper> =
            Int2ObjectOpenHashMap<PreciseSleeper>(values.size).apply {
                for (value in values) {
                    put(value.type, value)
                }
            }

        operator fun get(type: Int): PreciseSleeper? =
            typeToSleeper.get(type) // need to use explicit `get` as operator would refer to garbage producing function
    }

}
