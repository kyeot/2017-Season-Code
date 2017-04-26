package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;

import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ShooterBase extends Subsystem {
	
	private VictorSP shooterMotor1;
	
	public ShooterBase() {
		shooterMotor1 = new VictorSP(RobotMap.SHOOTER_WHEEL_ID);
	}
	
	public void setShooterSpeedVbus(double vbusOutput){
		shooterMotor1.set(vbusOutput);
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterDrive());
    }
}

