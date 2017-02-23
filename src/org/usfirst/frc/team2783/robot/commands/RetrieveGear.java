package org.usfirst.frc.team2783.robot.commands;

import org.usfirst.frc.team2783.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetrieveGear extends Command {
	
	double sensor1Value;
	double sensor2Value;

	boolean isEqual = false;
	
    public RetrieveGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	sensor1Value = Robot.usSensor1.getValue();
    	sensor2Value = Robot.usSensor2.getValue();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	if(sensor1Value == sensor2Value){
    		isEqual = true;
    		
    	}
    	
    	else{
    		Robot.swerveBase.swerveDrive(0, 0, 0.25, true);
    	
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isEqual;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}