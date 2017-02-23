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
	private VictorSP agitatorMotor;
	
	public ShooterBase() {
		shooterMotor1 = new VictorSP(RobotMap.SHOOTER_WHEEL_1_ID);
		agitatorMotor = new VictorSP(RobotMap.AGITATOR_MOTOR_ID);
	}
	
	public void setAgitatorSpeedVbus(double vbusOutput){
		agitatorMotor.set(vbusOutput);
	}
	
	public void setShooterSpeedVbus(double vbusOutput){
		shooterMotor1.set(vbusOutput);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterDrive());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    }
}

