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

package org.jire.overwatcheat.aimbot

import org.jire.overwatcheat.Keyboard

class ToggleUIThread(
    private val keyboardId: Int,
    private vararg val keyCodes: Int
) : Thread("Toggle UI") {

    private val keyCodesReversed = keyCodes.reversedArray()

    override fun run() {
        while (true) {
            if (AimBotState.toggleUI) {
                toggleUI(keyboardId)

                AimBotState.toggleUI = false
            } else sleep(1)
        }
    }

    private fun toggleUI(deviceId: Int) {
        for (key in keyCodes) {
            Keyboard.pressKey(key, deviceId)
        }

        sleep(1)

        for (key in keyCodesReversed) {
            Keyboard.releaseKey(key, deviceId)
        }
    }

}
