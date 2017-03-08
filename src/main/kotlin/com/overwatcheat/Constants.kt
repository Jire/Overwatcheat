package com.overwatcheat

import com.overwatcheat.util.Screen

const val HP_BAR_COLOR = 0xFF_00_13
const val HP_BAR_COLOR_TOLERANCE = 2

const val X_OFFSET_1080p = 55
const val Y_OFFSET_1080p = 54

val X_OFFSET = Math.ceil(X_OFFSET_1080p * (Screen.WIDTH / 1920.0)).toInt()
val Y_OFFSET = Math.ceil(Y_OFFSET_1080p * (Screen.HEIGHT / 1080.0)).toInt()