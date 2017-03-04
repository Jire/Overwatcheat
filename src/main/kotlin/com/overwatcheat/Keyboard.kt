package com.overwatcheat

import com.overwatcheat.User32.keybd_event

fun keyState(virtualKeyCode: Int) = User32.GetKeyState(virtualKeyCode)

fun keyPressed(virtualKeyCode: Int) = keyState(virtualKeyCode) < 0

fun keyReleased(virtualKeyCode: Int) = !keyPressed(virtualKeyCode)

const val KEYEVENTF_EXTENDEDKEY = 0x1
const val KEYEVENTF_KEYUP = 0x2

fun press(key: Int, state: Int = KEYEVENTF_EXTENDEDKEY) = keybd_event(key.toByte(), 0, state, 0) > 0

fun release(key: Int) = press(key, KEYEVENTF_EXTENDEDKEY or KEYEVENTF_KEYUP)