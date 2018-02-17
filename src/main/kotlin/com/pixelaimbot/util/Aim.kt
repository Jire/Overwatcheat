package com.pixelaimbot.util

import com.pixelaimbot.settings.*
import com.pixelaimbot.util.Screen
import com.pixelaimbot.util.User32
import com.pixelaimbot.util.moveMouse
import com.pixelaimbot.util.*
import com.pixelaimbot.util.extensions.refresh
import com.pixelaimbot.util.extensions.set
import com.sun.jna.platform.win32.WinDef.POINT
import java.util.concurrent.ThreadLocalRandom.current as tlr

private val mousePos = ThreadLocal.withInitial { POINT() }
private val target = ThreadLocal.withInitial { POINT() }

private val delta = ThreadLocal.withInitial { Vector() }

fun randInt(min: Int, max: Int) = tlr().nextInt(min, max)
fun randInt(min: Int) = tlr().nextInt(min)
fun randInt() = tlr().nextInt()


private fun getAngle(x:Int, y:Int):Double {
  return 1.5 * Math.PI - Math.atan2(y.toDouble(), x.toDouble()) //note the atan2 call, the order of paramers is y then x
}

fun applyFlatSmoothing(currentAngle: Angle, destinationAngle: Angle, smoothing: Double) = destinationAngle.apply {
	x -= currentAngle.x
	y -= currentAngle.y
	z = 0.0
	normalize()
	
	x = currentAngle.x + x / 100 * (100 / smoothing)
	y = currentAngle.y + y / 100 * (100 / smoothing)
	
	normalize()
}

fun flatAim(currentAngle: Angle, destinationAngle: Angle, smoothing: Double, sensMultiplier: Double = 1.0) {
	//applyFlatSmoothing(currentAngle, destinationAngle, smoothing)
	//if (!destinationAngle.isValid()) return
	
	val delta = delta.get()
	delta.set(currentAngle.y - destinationAngle.y, currentAngle.x - destinationAngle.x, 0.0)
	
	var sens = GAME_SENSITIVITY * sensMultiplier
	if (sens < GAME_SENSITIVITY) sens = GAME_SENSITIVITY
	
	val dx = Math.round(delta.x / (sens * 0.022))
	val dy = Math.round(-delta.y / (sens * 0.022))
	
	mouseMove((dx / 2).toInt(), (dy / 2).toInt())
}

fun pathAim(currentAngle: Angle, destinationAngle: Angle, smoothing: Int,
            randomSleepMax: Int = 10, staticSleep: Int = 2,
            sensMultiplier: Double = 1.0, perfect: Boolean = false) {
	if (!destinationAngle.isValid()) return
	
	val delta = delta.get()
	delta.set(currentAngle.y - destinationAngle.y, currentAngle.x - destinationAngle.x, 0.0)
	
	var sens = GAME_SENSITIVITY * sensMultiplier
	if (sens < GAME_SENSITIVITY) sens = GAME_SENSITIVITY
	if (perfect) sens = 1.0
	
	val dx = Math.round(delta.x / (sens * 0.022))
	val dy = Math.round(-delta.y / (sens * 0.022))
	
	val mousePos = mousePos.get().refresh()
	
	val target = target.get()
	target.set((mousePos.x + (dx / 2)).toInt(), (mousePos.y + (dy / 2)).toInt())
	
	if (target.x <= 0 || target.x >= Screen.CENTER_X + Screen.WIDTH
			|| target.y <= 0 || target.y >= Screen.CENTER_Y + Screen.HEIGHT) return
	
	if (perfect) {
		mouseMove((dx / 2).toInt(), (dy / 2).toInt())
		//Thread.sleep(20)
	} else HumanMouse.fastSteps(mousePos, target) { steps, i ->
		val point = target
		mousePos.refresh()
		
		val tx = point.x - mousePos.x
		val ty = point.y - mousePos.y
		
		var halfIndex = steps / 2
		if (halfIndex == 0) halfIndex = 1
		mouseMove(tx / halfIndex, ty / halfIndex)
		
		val sleepingFactor = smoothing / 100.0
		val sleepTime = Math.floor(staticSleep.toDouble()
				+ randInt(randomSleepMax)
				+ randInt(i)) * sleepingFactor
		if (sleepTime > 0) Thread.sleep(sleepTime.toLong())
	}
}

