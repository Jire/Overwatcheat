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

import net.openhft.affinity.AffinityLock
import net.openhft.chronicle.core.OS
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Threads {

    private val logger: Logger = LoggerFactory.getLogger(Threads::class.java)

    private val availableProcessors =
        1.coerceAtLeast(Runtime.getRuntime().availableProcessors())

    private const val DEFAULT_THREADS_PER_CORE = 1

    val threadsPerCore by lazy {
        if (OS.isWindows()) {
            try {
                var physicalCores = 0

                val exec: Process =
                    Runtime.getRuntime()
                        .exec(
                            arrayOf("wmic", "CPU", "Get", "NumberOfCores", "/Format:List"),
                            emptyArray()
                        )
                exec.inputReader().use { reader ->
                    do {
                        val line = reader.readLine() ?: break
                        if (line.startsWith("NumberOfCores=")) {
                            physicalCores = line.substringAfter("NumberOfCores=").toIntOrNull() ?: continue
                            break
                        }
                    } while (true)
                }

                if (physicalCores > 0) {
                    return@lazy availableProcessors / physicalCores
                }
            } catch (e: Exception) {
                logger.error("Failed to determine threads-per-core on Windows OS", e)
            }
            DEFAULT_THREADS_PER_CORE
        } else AffinityLock.cpuLayout().threadsPerCore()
    }

    fun preciseSleep(totalNanos: Long) {
        val startTime = System.nanoTime()

        // busy-waiting
        while (System.nanoTime() - startTime < totalNanos) {
            Thread.onSpinWait()
        }
    }

}
