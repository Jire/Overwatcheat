package com.overwatcheat

fun keyState(virtualKeyCode: Int) = User32.GetKeyState(virtualKeyCode)

fun keyPressed(virtualKeyCode: Int) = keyState(virtualKeyCode) < 0

fun keyReleased(virtualKeyCode: Int) = !keyPressed(virtualKeyCode)