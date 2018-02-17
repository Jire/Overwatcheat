package com.pixelaimbot.settings

/*
	[*] This is important:
	* If you want to use PixelAimBot for a HP BAR or something that needs to use the certain calculation to get moveX and moveY:
	** x_offset = ceil(X_OFFSET_1080p * (Screen.WIDTH / 1920.0))
	** y_offset = ceil(Y_OFFSET_1080p * (Screen.HEIGHT / 1920.0))
		[*] and then on the aimbot code
		* val absX = x + X_OFFSET + CAPTURE_OFFSET_X
		* val absY = y + Y_OFFSET + CAPTURE_OFFSET_Y
		* aim(absX, absY)

	** If you want to use it for Overwatch just set the variable true, but if you want to tweak the number then make it false and change to your liking.
	** [MAKE SURE THE VALUES ARE 0 AND Overwatch false IF YOU DON'T WANT TO USE THIS]
	** Make sure the values are for 1080p and not 900p or something like that, you can always use:
	** (Example for 1600x900) -> x_offset_1080p = (1920* the_x_offset_you_got_from_900p)/1600
								 y_offset_1080p = (1080* the_y_offset_you_got_from_900p)/900
*/
var Overwatch = true

// Default values for Overwatch: [int]
var x_offset_1080p = 55
var y_offset_1080 = 54

// Use this if you want it to use enemy red outline and HP Bar instead of HP Bar only
// [*] Default: Red min/max -> 55, 70; Green max -> (100- red min)/2; Blue max -> green max
var UseColorRate = true
var RATE_RED_MIN = 55
var RATE_RED_MAX = 70
var RATE_GREEN_MAX = ((100 - RATE_RED_MIN)/2).toInt()
var RATE_BLUE_MAX = RATE_GREEN_MAX

// The speed of the aim, lower being slower, higher being faster [float/double]
var speed = 4.1

// The minimum amount of milliseconds to sleep after each scan [int]
var sleepMin = 1
// The maximum amount of milliseconds to sleep after each scan [int]
var sleepMax = 6

// The virtual key code key which activates the aimbot when held [int]
// 1 -> Left		2 -> right		...
var aimKey = 1

// Calculates FoV box width by dividing screen width by this value [float/double]
var boxWidthDivisor = 6.0
// Calculates FoV box height by dividing screen height by this value [float/double]
var boxHeightDivisor = 3.5

// Your game's sensitivity
// ** Not yet implemented in the code but should be good for a few games
var GAME_SENSITIVITY = 6.80

// Color to look for, HEX as 0xFF_FF_FF
var COLOR = 0xFF_00_13
// Color's tolerance abs(hex color - hp bar color) must be <= the tolerance [int]
var COLOR_TOLERANCE = 2