package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.Climb;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ClimberBase extends Subsystem {
	
	private VictorSP climberMotor;
	
	public ClimberBase(){
		climberMotor = new VictorSP(RobotMap.CLIMBER_WHEEL_ID);
	}

	public void setClimberSpeedVbus(double vbusOutput) {
		climberMotor.set(vbusOutput);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new Climb());
    }
}

