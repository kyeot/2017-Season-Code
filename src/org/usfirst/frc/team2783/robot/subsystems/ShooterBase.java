package org.usfirst.frc.team2783.robot.subsystems;

import org.usfirst.frc.team2783.robot.Robot;
import org.usfirst.frc.team2783.robot.RobotMap;
import org.usfirst.frc.team2783.robot.commands.ShooterDrive;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterBase extends Subsystem {

	private Talon shooterWheelMotor = new Talon(9);
	
	private Talon gathererMotor = new Talon(10);
	
	private Victor gearShifter = new Victor(11);
	
	public void setShooterSpeedVbus(double vbusOutput) {
		shooterWheelMotor.set(vbusOutput);
		
	}
	
	public void setGathererSpeedVbus(double vbusOutput) {
		gathererMotor.set(vbusOutput);
		
	}
	
	public void shiftGear(double vbusOutput){
		gearShifter.set(vbusOutput);
		
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
    	setDefaultCommand(new ShooterDrive());
    	
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

