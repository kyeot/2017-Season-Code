package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.Climb;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberBase extends Subsystem {
	
	private CANTalon climberMotor;
	
	public ClimberBase(){
		climberMotor = new CANTalon(RobotMap.CLIMBER_WHEEL_ID);
	}

	public void setClimberSpeedVbus(double vbusOutput) {
		climberMotor.set(vbusOutput);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new Climb());
    }
}

