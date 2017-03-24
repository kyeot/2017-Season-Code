package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.OI;
import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShooterDrive extends Command {
	
	double loop;
	
    public ShooterDrive() {
    	requires(Robot.shooterBase);
    	
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(OI.manipulator.getRawButton(8)) {
    		Robot.shooterBase.setShooterSpeedVbus(0.792783);
    		Robot.shooterBase.setAgitatorSpeedVbus(0.95);
    	} else {
    		Robot.shooterBase.setAgitatorSpeedVbus(0);
    		Robot.shooterBase.setShooterSpeedVbus(0);
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
