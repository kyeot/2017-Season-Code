package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetrieveGear extends Command {
	
	AnalogInput sensor1;
	AnalogInput sensor2;
	
    public RetrieveGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	sensor1 = new AnalogInput(0);
    	sensor2 = new AnalogInput(1);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	System.out.println("1: " + sensor1.getValue() + "2: " + sensor2.getValue());
    	
    	if(sensor1.getValue() == sensor2.getValue()) {
    		Robot.swerveBase.swerveDrive(0, 0, 0, true);
    	} 
    	else {
    		Robot.swerveBase.swerveDrive(0, 0, 0.3, true);
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
