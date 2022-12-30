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

package org.jire.overwatcheat.nativelib.interception

import java.lang.foreign.*

object Interception {

    private val linker = Linker.nativeLinker()
    private val interception = SymbolLookup.libraryLookup("interception", MemorySession.global())

    private val interception_create_context = linker.downcallHandle(
        interception.lookup("interception_create_context").get(),
        FunctionDescriptor.of(ValueLayout.ADDRESS)
    )

    val interceptionContext = interception_create_context()

    private fun interception_create_context() = interception_create_context.invokeExact() as MemoryAddress

    val interceptionMouseStrokeLayout: GroupLayout = MemoryLayout.structLayout(
        ValueLayout.JAVA_SHORT,
        ValueLayout.JAVA_SHORT,
        ValueLayout.JAVA_SHORT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT,
        ValueLayout.JAVA_INT
    )

    private fun interceptionSendSymbol() = interception.lookup("interception_send").get()

    private val interception_send = linker.downcallHandle(
        interceptionSendSymbol(),
        FunctionDescriptor.of(
            ValueLayout.JAVA_INT,
            ValueLayout.ADDRESS, ValueLayout.JAVA_INT, interceptionMouseStrokeLayout, ValueLayout.JAVA_INT
        )
    )

    fun interception_send(context: Addressable, device: Int, stroke: MemorySegment, strokeCount: Int) =
        interception_send.invokeExact(
            context,
            device,
            stroke,
            strokeCount
        ) as Int

}
