package org.usfirst.frc.team2783.robot.triggers;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.buttons.Button;

public class LimitSwitch extends Button {
	
	public AnalogInput limitSwitch;
	
	public LimitSwitch(int id){
		limitSwitch = new AnalogInput(id);
	}

	@Override
	public boolean get() {
		// TODO Auto-generated method stub
		if(Robot.oi.overrideToggle.getValue()){

			return false;
		}
		
		else{
			return limitSwitch.getValue() < 100;
		}
	}

}
