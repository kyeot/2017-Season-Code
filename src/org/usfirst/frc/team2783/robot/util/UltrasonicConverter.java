package org.usfirst.frc.team2783.robot.util;

import edu.wpi.first.wpilibj.AnalogInput;

public class UltrasonicConverter {

	AnalogInput analog;
	
	public UltrasonicConverter(int port){
		this.analog = new AnalogInput(port);
	}
	
	public double getCentimeters(){
		return analog.getValue()/20;
	}
}
