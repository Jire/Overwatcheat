import com.pixelaimbot.settings.*

// (*) Both need to be correct for the program to shoot.
// (*) Must read file (if you plan to use triggerbot)

/*
	* Just like the aimbot needs either the moveX or moveY to be > 0 or < 0
	* the triggerbot must have its settings for it to trigger.

	* Make SURE both values are not 0 if you are using the program to get a HP BAR.
	* The default values are for Overwatch, but you can tweak it to your liking.

	* The program assumes your fire key is 1 (left mouse key)
	* Make sure it is left mouse.


	* The max value of x for the program to trigger. 
	* Triggered on same fuction that triggers aimbot, if x is higher than moveX it won't trigger.
	* must be -> moveX <= SETTINGS.moveX && moveX >= -SETTINGS.moveX
	* (If it's 80 then it must be between 80 and -80)
*/
moveX = 80

/*
	* The max value of y for the program to trigger. 
	* Triggered on same fuction that triggers aimbot, if y is higher than moveY it won't trigger.
	* must be -> moveY <= SETTINGS.moveY
	* (If it's 5 then it must be lower or equal than 5)
*/
moveY = 5