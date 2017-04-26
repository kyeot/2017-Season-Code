package org.usfirst.frc.team2783.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class Dpad extends Trigger {
	//Creates joystick and Dpad values
	Joystick joystick;
	double povTargetAngle;
	
	//Takes value from Dpad
	public Dpad(Joystick joystick, double povTargetAngle){
		this.joystick = joystick;
		this.povTargetAngle = povTargetAngle;
	}
	
	//Gets the value of the Dpad (0, 90, 270, 360)
    public boolean get(){
        return joystick.getPOV(0) == this.povTargetAngle;
    }
}
