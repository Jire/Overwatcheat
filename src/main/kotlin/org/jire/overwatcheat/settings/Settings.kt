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

package org.jire.overwatcheat.settings

import java.awt.event.KeyEvent
import java.io.File

object Settings {

    internal val nameToSetting: MutableMap<String, Setting> = HashMap()

    val aimKey by IntSetting("aim_key", 1)
    val aimMode by IntSetting("aim_mode", 0)
    val flickPixels by IntSetting("flick_shoot_pixels", 5)
    val flickPause by IntSetting("flick_pause_duration", 300)
    val sensitivity by FloatSetting("sensitivity", 15.0F)
    val fps by DoubleSetting("fps", 60.0)
    val aimDurationMillis by FloatSetting("aim_duration_millis", 3.5F)
    val aimDurationMultiplierBase by FloatSetting("aim_duration_multiplier_base", 1.0F)
    val aimDurationMultiplierMax by FloatSetting("aim_duration_multiplier_max", 2.0F)
    val aimMaxMovePixels by IntSetting("aim_max_move_pixels", 3)
    val aimJitterPercent by IntSetting("aim_jitter_percent", 0)
    val aimMinTargetWidth by IntSetting("aim_min_target_width", 8)
    val aimMinTargetHeight by IntSetting("aim_min_target_height", 8)
    val boxWidth by IntSetting("box_width", 256)
    val boxHeight by IntSetting("box_height", 256)
    val maxSnapDivisor by FloatSetting("max_snap_divisor", 2.0F)
    val targetColors by HexIntArraySetting("target_colors", intArrayOf(0xd521cd, 0xd722cf, 0xd623ce, 0xd722ce, 0xd621cd, 0xce19ca, 0xd11ccb, 0xd21dca, 0xc818cf, 0xd722cd, 0xd722ce, 0xcd19c9, 0xc617d3, 0xcb17c5, 0xda25d3, 0xce24cc, 0xd328cc, 0xdb32ef, 0xbd15c4, 0xdc5bea, 0xda59eb, 0xd959e9, 0xf444fb, 0xcf1ac9, 0xd422d4, 0xd923cd, 0xe53af2, 0xd321d3, 0xe539f3, 0xe035ed, 0xd822cc, 0xe83df5, 0xd11fd1, 0xd622d0, 0xd21dcc, 0xd429e2, 0xe537ef, 0xd923cd, 0xe136ee, 0xd321d3, 0xe63bf3, 0xd722cf, 0xe036ee, 0xd72ce6, 0xd428e1, 0xd321d3, 0xd21dcc, 0xdf34ed, 0xd822cc, 0xe434e6, 0xd43ddf, 0xde30e4, 0xbe0dbe, 0xd823d3, 0xc814c4, 0xc20ab7, 0xde1ec1, 0xca16c6, 0xc30ebe, 0xbb0fbf, 0xc510bf, 0xc10cbc, 0xd21cb6, 0xca14c5, 0xb80cd1, 0xae0ea8, 0xbf0ec3, 0xd415c1, 0xbc22b7, 0xd317c4, 0xb1179d, 0xbc0fb4, 0xcc47c7, 0xb834b5, 0xdc2cd9, 0xd727d5, 0xde30da, 0xc834c6))
    val targetColorTolerance by IntSetting("target_color_tolerance", 8)
    val windowTitleSearch by StringSetting("window_title_search", "Overwatch")
    val mouseId by IntSetting("mouse_id", 11)
    val keyboardId by IntSetting("keyboard_id", 1)
    val aimOffsetX by FloatSetting("aim_offset_x", 1.00F)
    val aimOffsetY by FloatSetting("aim_offset_y", 0.75F)
    val enableOverlay by BooleanSetting("enable_overlay", false)
    val toggleInGameUI by BooleanSetting("toggle_in_game_ui", true)
    val toggleKeyCodes by IntArraySetting("toggle_key_codes", intArrayOf(KeyEvent.VK_ALT, KeyEvent.VK_Z))

    const val DEFAULT_FILE = "overwatcheat.cfg"

    fun read(filePath: String = DEFAULT_FILE) = read(File(filePath))

    fun read(file: File) {
        file.readLines().forEach {
            if (!it.startsWith('#') && it.contains('=')) {
                val split = it.split("=")

                val settingName = split[0]
                val setting = nameToSetting[settingName] ?: return@forEach
                val settingValue = split[1]
                setting.parse(settingValue)
            }
        }
    }

}
