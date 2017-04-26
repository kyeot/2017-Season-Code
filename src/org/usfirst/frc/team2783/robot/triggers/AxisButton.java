package org.usfirst.frc.team2783.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

public class AxisButton extends Trigger {
	
	private Joystick joystick;
	private int axis;

	public AxisButton(Joystick joystick, int axis){
		this.joystick = joystick;
		this.axis = axis;
	}
	
	@Override
	public boolean get() {
		return Math.abs(joystick.getRawAxis(axis)) > 0.2;
	}
	
	

}
