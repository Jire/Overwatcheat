package com.overwatcheat.util

import java.awt.Dimension
import java.awt.Toolkit

object Screen {
	
	val DIMENSION: Dimension = Toolkit.getDefaultToolkit().screenSize
	
	val WIDTH = DIMENSION.width
	val HEIGHT = DIMENSION.height
	
	val CENTER_X = WIDTH / 2
	val CENTER_Y = HEIGHT / 2
	
}