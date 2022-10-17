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

package org.jire.overwatcheat.overlay

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration.GLEmulation
import com.sun.jna.platform.win32.WinUser
import org.jire.overwatcheat.Screen
import org.jire.overwatcheat.nativelib.User32
import org.jire.overwatcheat.overlay.transparency.AccentFlags
import org.jire.overwatcheat.overlay.transparency.AccentStates
import org.jire.overwatcheat.overlay.transparency.WindowCompositionAttributeData
import org.jire.overwatcheat.settings.Settings
import org.lwjgl.glfw.GLFW
import java.security.SecureRandom
import kotlin.concurrent.thread
import kotlin.math.min
import org.lwjgl.system.windows.User32 as LWJGLUser32

object OverlayManager {

    @Volatile
    var opened = false

    @Volatile
    var myHWND = -1L

    fun open(captureOffsetX: Int, captureOffsetY: Int) {
        val title = SecureRandom().nextLong().toString()
        val overlay = Overlay(title, captureOffsetX, captureOffsetY)
        val config = Lwjgl3ApplicationConfiguration().apply {
            setTitle(title)
            setWindowPosition(0, 0)
            setWindowedMode(Screen.WIDTH, Screen.HEIGHT)
            setOpenGLEmulation(GLEmulation.GL30, 4, 6)
            setResizable(false)
            setDecorated(false)
            useVsync(false)
            GLFW.glfwSwapInterval(0)
            GLFW.glfwWindowHint(GLFW.GLFW_DOUBLEBUFFER, GLFW.GLFW_TRUE)
            setBackBufferConfig(8, 8, 8, 8, 16, 0, 4) // samples 4

            val fps = Settings.fps.toInt()
            setForegroundFPS(fps)
            setIdleFPS(min(30, fps))
        }
        thread(name = "Overlay") {
            Lwjgl3Application(overlay, config)
        }
    }

    fun makeTransparent(myHWND: Long) = User32.SetWindowCompositionAttribute(
        myHWND,
        WindowCompositionAttributeData(
            AccentState = AccentStates.ACCENT_ENABLE_TRANSPARENTGRADIENT, AccentFlags = AccentFlags.Transparent
        )
    )

    fun makeUndecorated(myHWND: Long) = User32.SetWindowLongA(
        myHWND,
        LWJGLUser32.GWL_EXSTYLE,
        LWJGLUser32.WS_EX_COMPOSITED or
                LWJGLUser32.WS_EX_LAYERED or
                LWJGLUser32.WS_EX_TRANSPARENT or
                LWJGLUser32.WS_EX_TOOLWINDOW or LWJGLUser32.WS_EX_TOPMOST
    )

    fun makeClickthrough(myHWND: Long) = User32.SetWindowLongA(
        myHWND,
        WinUser.GWL_STYLE,
        User32.GetWindowLongA(myHWND, WinUser.GWL_STYLE)
                and WinUser.WS_OVERLAPPEDWINDOW.inv()
    )

    init {
        System.setProperty("org.lwjgl.opengl.Window.undecorated", "true")
    }

}