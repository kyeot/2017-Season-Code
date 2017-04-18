package org.usfirst.frc.team2783.robot.triggers;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.buttons.Button;

public class LimitSwitch extends Button {
	
	AnalogInput limitSwitch;
	
	public LimitSwitch(int id){
		limitSwitch = new AnalogInput(id);
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		return limitSwitch.getValue() < 100;
	}

}
