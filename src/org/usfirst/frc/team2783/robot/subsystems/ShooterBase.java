package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterBase extends Subsystem {

	private CANTalon shooterWheelMotor;
	
	private CANTalon gathererMotor;
	
	public void setShooterSpeedVbus(double vbusOutput) {
		shooterWheelMotor.set(vbusOutput);
	}
	
	public void setGathererSpeedVbus(double vbusOutput) {
		gathererMotor.set(vbusOutput);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterDrive());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

