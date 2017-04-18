package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.ActiveGearDrive;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ActiveGearBase extends Subsystem {
	
	private VictorSP gearRoller;
	private CANTalon gearLifter;
	
	private Encoder lifterEnc;
	
	public ActiveGearBase(){
		gearRoller = new VictorSP(RobotMap.GEAR_ROLLER_ID);
		gearLifter = new CANTalon(RobotMap.GEAR_HOLDER_ID);
		
		lifterEnc = new Encoder(
							new DigitalInput(8),
							new DigitalInput(9));
		lifterEnc.setDistancePerPulse(0.875);
	}
	
	public void spinRoller(double vbusOutput){
		gearRoller.set(vbusOutput);
	}
	
	public void setLifterSpeedVbus(double speed) {
		if(getLifterAngle() < 90 && getLifterAngle() > 0) {
			gearLifter.set(speed);
		} else {
			gearLifter.set(0);
		}
    	
    }
	
	public double getLifterAngle() {
		if(lifterEnc != null) {
			return lifterEnc.getDistance();
		} else {
			return -1.0;
		}
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new ActiveGearDrive());
    }
}

