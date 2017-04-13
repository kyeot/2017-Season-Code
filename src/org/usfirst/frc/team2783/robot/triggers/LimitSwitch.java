package org.usfirst.frc.team2783.robot.triggers;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.buttons.Button;

public class LimitSwitch extends Button {
	
	DigitalInput limitSwitch;
	
	public LimitSwitch(int id){
		limitSwitch = new DigitalInput(id);
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return !limitSwitch.get();
	}

}
